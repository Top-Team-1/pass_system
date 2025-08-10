document.addEventListener('DOMContentLoaded', async () => {
    const jwt = localStorage.getItem('jwt');

    if (!jwt) {
        alert('Вы не авторизованы');
        location.href = 'index.html';
        return;
    }

    try {
        const response = await fetch('http://localhost:8080/api/me', {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${jwt}`
            }
        });

        if (!response.ok) {
            throw new Error('Ошибка получения информации о пользователе');
        }

        const user = await response.json();

        document.getElementById('userInfo').innerText = `Привет, ${user.firstName} ${user.lastName} [${user.role}]`;

        if (user.role === 'ADMIN') {
            document.getElementById('adminActions').style.display = 'block';
        } else {
            document.getElementById('userActions').style.display = 'block';
        }

    } catch (error) {
        console.error(error);
        alert('Ошибка при получении информации о пользователе');
        location.href = 'index.html';
    }
});

function logout() {
    localStorage.removeItem('jwt');
    location.href = 'index.html';
}


document.addEventListener('DOMContentLoaded', () => {
    const token = localStorage.getItem('jwt');

    if (!token) {
        alert('Вы не авторизованы');
        location.href = 'index.html';
        return;
    }


    fetch('http://localhost:8080/api/me', {
        headers: {
            'Authorization': `Bearer ${token}`
        }
    })
        .then(res => res.json())
        .then(user => {
            document.getElementById('userInfo').innerText = `Привет, ${user.firstName} ${user.lastName} (${user.role})`;

            if (user.role === 'ADMIN') {
                document.getElementById('adminActions').style.display = 'block';
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

function logout() {
    localStorage.removeItem('jwt');
    location.href = 'index.html';
}



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

let currentPage = 0;
let currentSort = 'id';
let currentDirection = 'asc';
const pageSize = 5;

function getAllUsers(page = 0, sort = 'id', direction = 'asc') {
    currentPage = page;
    currentSort = sort;
    currentDirection = direction;

    const url = `http://localhost:8080/api/user?page=${page}&size=${pageSize}&sort=${sort},${direction}`;

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

function sortUsers(field) {
    const newDirection = currentSort === field && currentDirection === 'asc' ? 'desc' : 'asc';
    getAllUsers(0, field, newDirection);
}

function deleteUser(id) {
    if (!id) {
        id = prompt("Введите ID пользователя для удаления:");
        if (!id) {
            alert("ID не введён, удаление отменено");
            return;
        }
    }

    if (!confirm(`Точно удалить пользователя с ID ${id}?`)) return;

    fetch(`http://localhost:8080/api/user/${id}`, {
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
            getAllUsers(currentPage, currentSort, currentDirection);
        })
        .catch(err => {
            console.error('Ошибка удаления:', err);
            alert('Ошибка при удалении пользователя');
        });
}

function getAllTerritories(page = 0, sort = 'id', direction = 'asc') {
    currentTerritoryPage = page;
    currentTerritorySort = sort;
    currentTerritoryDirection = direction;

    const url = `http://localhost:8080/api/territory?page=${page}&size=${territoryPageSize}&sort=${sort},${direction}`;

    fetch(url, {
        method: 'GET',
        headers: {
            'Authorization': `Bearer ${localStorage.getItem('jwt')}`
        }
    })
        .then(res => {
            if (!res.ok) throw new Error(`Ошибка загрузки территорий: ${res.status}`);
            return res.json();
        })
        .then(data => {
            const territories = data.content;
            const totalPages = data.totalPages;
            const container = document.getElementById('userListContainer');

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
                        <td><button onclick="confirmDeleteTerritory(${t.id})">Удалить</button></td>
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
            console.error('Ошибка при получении территорий:', err);
            alert('Не удалось загрузить список территорий');
        });
}


function createTerritory() {
    const name = prompt('Название территории:');
    const address = prompt('Адрес:');
    const type = prompt('Тип территории (HOUSE, OFFICE, ENTERPRISE :');

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

function deleteTerritory(id) {
    if (!id) {
        id = prompt("Введите ID территории для удаления:");
        if (!id) {
            alert("ID не введён, удаление отменено");
            return;
        }
    }

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

function bindUserToTerritory() {
    const userId = prompt('Введите ID пользователя:');
    const territoryId = prompt('Введите ID территории:');

    if (!userId || !territoryId) {
        alert('Оба ID обязательны!');
        return;
    }

    if (isNaN(Number(userId)) || isNaN(Number(territoryId))) {
        alert('ID должны быть числами!');
        return;
    }

    const data = {
        userId: Number(userId),
        territoryId: Number(territoryId)
    };

    fetch('http://localhost:8080/api/user_territory', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${localStorage.getItem('jwt')}`
        },
        body: JSON.stringify(data)
    })
        .then(res => {
            if (res.ok) {
                return res.text();
            } else {
                return res.text().then(text => {
                    throw new Error(text);
                });
            }
        })
        .then(message => {
            alert(message);
        })
        .catch(err => {
            console.error('Ошибка при привязке пользователя к территории:', err);
            alert(`Ошибка: ${err.message || 'Не удалось привязать пользователя к территории'}`);
        });
}
const API_PASS = 'http://localhost:8080/api/pass';

function createPass() {
    const userId = prompt('ID пользователя:');
    const territoryId = prompt('ID территории:');
    const startDate = prompt('Дата начала (yyyy-MM-dd HH:mm):');
    const endDate = prompt('Дата окончания (yyyy-MM-dd HH:mm):');
    const type = prompt('Тип (TIMELESS или PERMANENT :');
    const firstName = prompt('Имя:');
    const lastName = prompt('Фамилия:');

    if (!userId || !territoryId || !startDate || !endDate || !type || !firstName || !lastName) {
        alert('Все поля обязательны!');
        return;
    }

    const data = {
        userId: Number(userId),
        territoryId: Number(territoryId),
        startDate,
        endDate,
        type,
        firstName,
        lastName
    };

    fetch('http://localhost:8080/api/pass', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${localStorage.getItem('jwt')}`
        },
        body: JSON.stringify(data)
    })
        .then(res => {
            if (res.ok) {
                alert('Пропуск создан!');
                getAllPasses(currentPassPage, currentPassSort, currentPassDirection);
            } else {
                return res.text().then(text => {
                    throw new Error(text);
                });
            }
        })
        .catch(err => {
            console.error('Ошибка создания пропуска:', err);
            alert('Ошибка создания пропуска');
        });
}

function deletePass(id) {
    if (!id) {
        id = prompt("Введите ID пропуска для удаления:");
        if (!id) {
            alert("ID не введён, удаление отменено");
            return;
        }
    }

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

let currentPassPage = 0;
let currentPassSort = 'id';
let currentPassDirection = 'asc';
const passPageSize = 5;

function getAllPasses(page = 0, sort = 'id', direction = 'asc') {
    currentPassPage = page;
    currentPassSort = sort;
    currentPassDirection = direction;

    fetch(`http://localhost:8080/api/pass?page=${page}&size=${passPageSize}&sort=${sort},${direction}`, {
        headers: {
            'Authorization': `Bearer ${localStorage.getItem('jwt')}`
        }
    })
        .then(res => {
            if (!res.ok) throw new Error(`Ошибка: ${res.status}`);
            return res.json();
        })
        .then(data => {
            const passes = data.content || [];
            const totalPages = data.totalPages || 1;
            renderPassTableWithPagination(passes, totalPages, page, sort, direction);
        })
        .catch(err => {
            console.error('Ошибка получения пропусков:', err);
            alert('Ошибка при получении пропусков');
        });
}

function renderPassTableWithPagination(passes, totalPages, page, sort, direction) {
    const container = document.getElementById('passListContainer');
    if (!container) {
        console.error('Контейнер passListContainer не найден');
        return;
    }

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
                    <th onclick="sortPasses('firstName')">Имя</th>
                    <th onclick="sortPasses('lastName')">Фамилия</th>
                    <th onclick="sortPasses('territoryName')">Территория</th>
                    <th onclick="sortPasses('startDate')">Начало</th>
                    <th onclick="sortPasses('endDate')">Окончание</th>
                    <th onclick="sortPasses('type')">Тип</th>
                    <th>Действия</th>
                </tr>
            </thead>
            <tbody>
    `;

    passes.forEach(pass => {
        html += `
            <tr>
                <td>${pass.id ?? ''}</td>
                <td>${pass.firstName ?? pass.user?.firstName ?? ''}</td>
                <td>${pass.lastName ?? pass.user?.lastName ?? ''}</td>
                <td>${pass.territoryName ?? ''}</td>
                <td>${pass.startDate ?? ''}</td>
                <td>${pass.endDate ?? ''}</td>
                <td>${pass.type ?? ''}</td>
                <td><button onclick="deletePass(${pass.id})">Удалить</button></td>
            </tr>
        `;
    });

    html += `</tbody></table>`;

    html += `<div style="margin-top:10px">`;
    for (let i = 0; i < totalPages; i++) {
        html += `<button onclick="getAllPasses(${i}, '${sort}', '${direction}')" ${i === page ? 'disabled' : ''}>${i + 1}</button> `;
    }
    html += `</div>`;

    container.innerHTML = html;
}
function sortPasses(field) {
    const newDirection = currentPassSort === field && currentPassDirection === 'asc' ? 'desc' : 'asc';
    getAllPasses(0, field, newDirection);
}

document.addEventListener('DOMContentLoaded', () => {
    const token = localStorage.getItem('jwt');

    if (!token) {
        alert('Вы не авторизованы');
        location.href = 'index.html';
        return;
    }

    // Получение данных пользователя по токену
    fetch('http://localhost:8080/api/me', {
        headers: {
            'Authorization': `Bearer ${token}`
        }
    })
        .then(res => res.json())
        .then(user => {
            document.getElementById('userInfo').innerText = `Привет, ${user.firstName} ${user.lastName} (${user.role})`;

            if (user.role === 'ADMIN') {
                document.getElementById('adminActions').style.display = 'block';
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

function logout() {
    localStorage.removeItem('jwt');
    location.href = 'index.html';
}


let currentTerritoryPage = 0;
let currentTerritorySort = 'id';
let currentTerritoryDirection = 'asc';
const territoryPageSize = 5;




// Сортировка территорий
function sortTerritories(field) {
    const newDirection = currentTerritorySort === field && currentTerritoryDirection === 'asc' ? 'desc' : 'asc';
    getAllTerritories(0, field, newDirection);
}



// Подтверждение удаления территории
function confirmDeleteTerritory(id) {
    if (!confirm(`Точно удалить территорию с ID ${id}?`)) return;
    deleteTerritory(id);
}





// Методы USER
function getMyTerritories() {
    fetch('http://localhost:8080/api/territory/my', {
        headers: {
            'Authorization': `Bearer ${localStorage.getItem('jwt')}`
        }
    })
        .then(res => {
            if (!res.ok) throw new Error(`Ошибка: ${res.status}`);
            return res.json();
        })
        .then(data => {
            const container = document.getElementById('userListContainer');
            if (data.length === 0) {
                container.innerHTML = '<p>У вас нет привязанных территорий.</p>';
                return;
            }
            container.innerHTML = '<h4>Мои территории:</h4><ul>' +
                data.map(t => `<li>ID: ${t.id}, Название: ${t.name}</li>`).join('') + '</ul>';
        })
        .catch(err => {
            console.error('Ошибка получения моих территорий:', err);
            alert('Ошибка при загрузке территорий');
        });
}

function getMyPasses() {
    fetch('http://localhost:8080/api/pass/my', {
        headers: {
            'Authorization': `Bearer ${localStorage.getItem('jwt')}`
        }
    })
        .then(res => {
            if (!res.ok) throw new Error(`Ошибка: ${res.status}`);
            return res.json();
        })
        .then(data => {
            const container = document.getElementById('passListContainer');
            const passes = data.content || [];
            if (passes.length === 0) {
                container.innerHTML = '<p>У вас нет пропусков.</p>';
                return;
            }
            container.innerHTML = '<h4>Мои пропуска:</h4><ul>' +
                passes.map(p => `<li>ID: ${p.id}, Тип: ${p.type}, Статус: ${p.status}</li>`).join('') + '</ul>';
        })
        .catch(err => {
            console.error('Ошибка получения моих пропусков:', err);
            alert('Ошибка при загрузке пропусков');
        });
}








