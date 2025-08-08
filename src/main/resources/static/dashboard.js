// ================ GLOBAL VARIABLES AND CONSTANTS ================

// Для пропусков
let currentPassPage = 0;
let currentPassSort = 'id';
let currentPassDirection = 'asc';
const passPageSize = 5;

// ========== HELPER FUNCTIONS FOR AUTHENTICATION ==========

// Функция для выхода из аккаунта
function logout() {
    localStorage.removeItem('jwt');
    location.href = 'index.html'; // перенаправление на главную страницу
}

// ========== ADMIN ACTIONS (USER MANAGEMENT) ==========

// Создание нового пользователя
function createUser() {
    const firstName = prompt('Имя:');
    const lastName = prompt('Фамилия:');
    const dateOfBirth = prompt('Дата рождения (yyyy-mm-dd):');
    const phone = prompt('Телефон:');
    const password = prompt('Пароль:');

    if (!firstName || !lastName || !dateOfBirth || !phone || !password) {
        alert('Все поля обязательны!');
        return;
    }

    const data = {
        firstName,
        lastName,
        dateOfBirth,
        phone,
        password
    };

    fetch('http://localhost:8080/api/user', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${localStorage.getItem('jwt')}`
        },
        body: JSON.stringify(data)
    })
        .then(res => {
            if (res.ok) {
                alert('Пользователь создан!');
                return res.json();
            } else {
                return res.text().then(text => {
                    throw new Error(text);
                });
            }
        })
        .catch(err => {
            console.error('Ошибка создания пользователя:', err);
            alert('Ошибка создания пользователя');
        });
}

// Удаление пользователя
function deleteUser() {
    const userId = prompt('Введите ID пользователя для удаления:');
    if (!userId) return;

    fetch(`http://localhost:8080/api/user/${userId}`, {
        method: 'DELETE',
        headers: {
            'Authorization': `Bearer ${localStorage.getItem('jwt')}`
        }
    })
        .then(res => {
            if (res.ok) {
                alert(`Пользователь с id ${userId} удалён`);
            } else {
                return res.text().then(text => {
                    console.error('Ошибка удаления:', text);
                    alert(`Ошибка: ${text}`);
                });
            }
        })
        .catch(err => {
            console.error('Ошибка удаления:', err);
            alert('Ошибка при удалении пользователя');
        });
}

// Получение всех пользователей с пагинацией и сортировкой
function getAllUsers(page = 0, sort = 'id', direction = 'asc') {
    const url = `http://localhost:8080/api/user?page=${page}&size=${passPageSize}&sort=${sort},${direction}`;

    fetch(url, {
        headers: {
            'Authorization': `Bearer ${localStorage.getItem('jwt')}`
        }
    })
        .then(res => {
            if (!res.ok) throw new Error(`Ошибка: ${res.status}`);
            return res.json();
        })
        .then(data => {
            const users = data.content;
            const totalPages = data.totalPages;
            const container = document.getElementById('userListContainer');

            if (!users || users.length === 0) {
                container.innerHTML = '<p>Пользователи не найдены.</p>';
                return;
            }

            let html = `
                <h3>Список пользователей</h3>
                <table border="1" cellpadding="5" cellspacing="0">
                    <thead>
                        <tr>
                            <th onclick="sortUsers('id')">ID</th>
                            <th onclick="sortUsers('firstName')">Имя</th>
                            <th onclick="sortUsers('lastName')">Фамилия</th>
                            <th onclick="sortUsers('dateOfBirth')">Дата рождения</th>
                            <th onclick="sortUsers('phone')">Телефон</th>
                            <th>Действия</th>
                        </tr>
                    </thead>
                    <tbody>
            `;

            users.forEach(user => {
                html += `
                    <tr>
                        <td>${user.id}</td>
                        <td>${user.firstName}</td>
                        <td>${user.lastName}</td>
                        <td>${user.dateOfBirth}</td>
                        <td>${user.phone}</td>
                        <td><button onclick="deleteUser(${user.id})">Удалить</button></td>
                    </tr>
                `;
            });

            html += '</tbody></table>';

            // Пагинация
            html += `<div style="margin-top: 10px;">`;
            for (let i = 0; i < totalPages; i++) {
                html += `<button onclick="getAllUsers(${i}, '${sort}', '${direction}')"
                            ${i === page ? 'disabled' : ''}>${i + 1}</button> `;
            }
            html += `</div>`;

            container.innerHTML = html;
        })
        .catch(err => {
            console.error('Ошибка получения пользователей:', err);
            alert('Ошибка при получении пользователей');
        });
}

// Сортировка пользователей
function sortUsers(field) {
    const newDirection = currentPassSort === field && currentPassDirection === 'asc' ? 'desc' : 'asc';
    getAllUsers(0, field, newDirection);
}

// ========== ADMIN ACTIONS (TERRITORIES MANAGEMENT) ==========

// Создание новой территории
function createTerritory() {
    const name = prompt('Название территории:');
    const address = prompt('Адрес:');
    const type = prompt('Тип территории (например: PARK, BUILDING, ZONE и т.д.):');

    if (!name || !address || !type) {
        alert('Все поля обязательны!');
        return;
    }

    const data = {
        name,
        address,
        type
    };

    fetch('http://localhost:8080/api/territory', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${localStorage.getItem('jwt')}`
        },
        body: JSON.stringify(data)
    })
        .then(res => {
            if (res.ok) {
                alert('Территория создана!');
                return res.json();
            } else {
                return res.text().then(text => {
                    throw new Error(text);
                });
            }
        })
        .then(() => {
            getAllTerritories(currentTerritoryPage, currentTerritorySort, currentTerritoryDirection);
        })
        .catch(err => {
            console.error('Ошибка создания территории:', err);
            alert(`Ошибка: ${err.message || 'Неизвестная ошибка'}`);
        });
}

// Получение всех территорий с пагинацией и сортировкой
function getAllTerritories(page = 0, sort = 'id', direction = 'asc') {
    currentTerritoryPage = page;
    currentTerritorySort = sort;
    currentTerritoryDirection = direction;

    const url = `http://localhost:8080/api/territory?page=${page}&size=${passPageSize}&sort=${sort},${direction}`;

    fetch(url, {
        headers: {
            'Authorization': `Bearer ${localStorage.getItem('jwt')}`
        }
    })
        .then(res => {
            if (!res.ok) throw new Error(`Ошибка: ${res.status}`);
            return res.json();
        })
        .then(data => {
            const territories = data.content;
            const totalPages = data.totalPages;
            const container = document.getElementById('territoryListContainer');

            if (!territories || territories.length === 0) {
                container.innerHTML = '<p>Территории не найдены.</p>';
                return;
            }

            let html = `
                <h3>Список территорий</h3>
                <table border="1" cellpadding="5" cellspacing="0">
                    <thead>
                        <tr>
                            <th onclick="sortTerritories('id')">ID</th>
                            <th onclick="sortTerritories('name')">Название</th>
                            <th>Адрес</th>
                            <th>Тип</th>
                            <th>Добавлено</th>
                            <th>Обновлено</th>
                            <th>Действия</th>
                        </tr>
                    </thead>
                    <tbody>
            `;

            territories.forEach(t => {
                html += `
                    <tr>
                        <td>${t.id}</td>
                        <td>${t.name}</td>
                        <td>${t.address}</td>
                        <td>${t.type}</td>
                        <td>${t.addedAt}</td>
                        <td>${t.updatedAt}</td>
                        <td><button onclick="deleteTerritory(${t.id})">Удалить</button></td>
                    </tr>
                `;
            });

            html += '</tbody></table>';

            // Пагинация
            html += `<div style="margin-top: 10px;">`;
            for (let i = 0; i < totalPages; i++) {
                html += `<button onclick="getAllTerritories(${i}, '${sort}', '${direction}')"
                            ${i === page ? 'disabled' : ''}>${i + 1}</button> `;
            }
            html += `</div>`;

            container.innerHTML = html;
        })
        .catch(err => {
            console.error('Ошибка получения территорий:', err);
            alert('Ошибка при получении территорий');
        });
}

// Сортировка территорий
function sortTerritories(field) {
    const newDirection = currentTerritorySort === field && currentTerritoryDirection === 'asc' ? 'desc' : 'asc';
    getAllTerritories(0, field, newDirection);
}

// Удаление территории
function deleteTerritory(id) {
    if (!confirm(`Точно удалить территорию с ID ${id}?`)) return;

    fetch(`http://localhost:8080/api/territory/${id}`, {
        method: 'DELETE',
        headers: {
            'Authorization': `Bearer ${localStorage.getItem('jwt')}`
        }
    })
        .then(res => {
            if (!res.ok) throw new Error(`Ошибка удаления: ${res.status}`);
            return res.text();
        })
        .then(msg => {
            alert(msg);
            getAllTerritories(currentTerritoryPage, currentTerritorySort, currentTerritoryDirection);
        })
        .catch(err => {
            console.error('Ошибка при удалении территории:', err);
            alert('Ошибка при удалении территории');
        });
}

// ========== ADMIN ACTIONS (PASS MANAGEMENT) ==========

// Создание пропуска
async function createPass() {
    const userId = prompt('Введите ID пользователя:');
    const territoryId = prompt('Введите ID территории:');
    const startDate = prompt('Введите начало срока пропуска (yyyy-MM-dd HH:mm):');
    const endDate = prompt('Введите конец срока пропуска (yyyy-MM-dd HH:mm):');
    const passType = prompt('Введите тип пропуска (например TEMPORARY или PERMANENT):');

    if (!userId || !territoryId || !startDate || !endDate || !passType) {
        alert('Все поля обязательны!');
        return;
    }

    const payload = {
        userId: parseInt(userId),
        territoryId: parseInt(territoryId),
        startDate,
        endDate,
        type: passType
    };

    try {
        const response = await fetch('http://localhost:8080/api/pass', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${localStorage.getItem('jwt')}`
            },
            body: JSON.stringify(payload)
        });

        if (!response.ok) {
            throw new Error('Ошибка создания пропуска.');
        }

        alert('Пропуск успешно создан.');
        getAllPasses();
    } catch (error) {
        console.error('Ошибка создания пропуска:', error);
        alert('Ошибка при создании пропуска.');
    }
}

// Получение всех пропусков с пагинацией и сортировкой
function getAllPasses(page = 0, sort = 'id', direction = 'asc') {
    currentPassPage = page;
    currentPassSort = sort;
    currentPassDirection = direction;

    const url = `http://localhost:8080/api/pass?page=${page}&size=${passPageSize}&sort=${sort},${direction}`;

    fetch(url, {
        headers: {
            'Authorization': `Bearer ${localStorage.getItem('jwt')}`
        }
    })
        .then(res => {
            if (!res.ok) throw new Error(`Ошибка: ${res.status}`);
            return res.json();
        })
        .then(data => {
            const passes = data.content;
            const totalPages = data.totalPages;
            const container = document.getElementById('passListContainer');

            if (!passes || passes.length === 0) {
                container.innerHTML = '<p>Пропуска не найдены.</p>';
                return;
            }

            let html = `
                <h3>Список пропусков</h3>
                <table border="1" cellpadding="5" cellspacing="0">
                    <thead>
                        <tr>
                            <th onclick="sortPasses('id')">ID</th>
                            <th onclick="sortPasses('startDate')">Начало срока</th>
                            <th onclick="sortPasses('endDate')">Окончание срока</th>
                            <th onclick="sortPasses('type')">Тип пропуска</th>
                            <th>Действия</th>
                        </tr>
                    </thead>
                    <tbody>
            `;

            passes.forEach(pass => {
                html += `
                    <tr>
                        <td>${pass.id}</td>
                        <td>${pass.startDate}</td>
                        <td>${pass.endDate}</td>
                        <td>${pass.type}</td>
                        <td><button onclick="deletePass(${pass.id})">Удалить</button></td>
                    </tr>
                `;
            });

            html += '</tbody></table>';

            // Пагинация
            html += `<div style="margin-top: 10px;">`;
            for (let i = 0; i < totalPages; i++) {
                html += `<button onclick="getAllPasses(${i}, '${sort}', '${direction}')"
                            ${i === page ? 'disabled' : ''}>${i + 1}</button> `;
            }
            html += `</div>`;

            container.innerHTML = html;
        })
        .catch(err => {
            console.error('Ошибка получения пропусков:', err);
            alert('Ошибка при получении пропусков');
        });
}

// Сортировка пропусков
function sortPasses(field) {
    const newDirection = currentPassSort === field && currentPassDirection === 'asc' ? 'desc' : 'asc';
    getAllPasses(0, field, newDirection);
}

// Удаление пропуска
function deletePass(id) {
    if (!confirm(`Точно удалить пропуск с ID ${id}?`)) return;

    fetch(`http://localhost:8080/api/pass/${id}`, {
        method: 'DELETE',
        headers: {
            'Authorization': `Bearer ${localStorage.getItem('jwt')}`
        }
    })
        .then(res => {
            if (!res.ok) throw new Error(`Ошибка удаления: ${res.status}`);
            return res.text();
        })
        .then(msg => {
            alert(msg);
            getAllPasses(currentPassPage, currentPassSort, currentPassDirection);
        })
        .catch(err => {
            console.error('Ошибка при удалении пропуска:', err);
            alert('Ошибка при удалении пропуска');
        });
}

// ========== INITIALIZATION ==========

// Основной обработчик событий при загрузке страницы
document.addEventListener('DOMContentLoaded', () => {
    const token = localStorage.getItem('jwt');

    if (!token) {
        alert('Вы не авторизованы');
        location.href = 'index.html';
        return;
    }

    // Загрузка данных пользователя
    fetch('http://localhost:8080/api/me', {
        headers: {
            'Authorization': `Bearer ${token}`
        }
    })
        .then(response => response.json())
        .then(user => {
            document.getElementById('userInfo').innerText = `Привет, ${user.firstName} ${user.lastName} (${user.role})`;

            if (user.role === 'ADMIN') {
                document.getElementById('adminActions').style.display = 'block';
                getAllUsers(); // получаем пользователей
                getAllTerritories(); // получаем территории
                getAllPasses(); // получаем пропуска
            } else {
                document.getElementById('userActions').style.display = 'block';
            }
        })
        .catch(err => {
            console.error('Ошибка получения данных пользователя:', err);
            alert('Ошибка авторизации');
            localStorage.removeItem('jwt');
            location.href = 'index.html';
        });
});
