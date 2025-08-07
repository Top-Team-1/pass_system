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

        const user = await response.json(); // предполагается, что сервер вернёт { firstName, lastName, role }

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

// ==== ADMIN METHODS ====

// 🔁 TODO: Вставь сюда вызовы своих API

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

// ===== ADMIN Actions =====

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

function getAllTerritories() {
    console.log('Получить все территории');
    // TODO: fetch('...', { method: 'GET', ... })
}

function createTerritory() {
    console.log('Создать территорию');
    // TODO: fetch('...', { method: 'POST', ... })
}

function deleteTerritory() {
    console.log('Удалить территорию');
    // TODO: fetch('...', { method: 'DELETE', ... })
}

function bindUserToTerritory() {
    console.log('Привязать пользователя к территории');
    // TODO: fetch('...', { method: 'POST', ... })
}

function createPass() {
    console.log('Создать пропуск');
    // TODO: fetch('...', { method: 'POST', ... })
}

function deletePass() {
    console.log('Удалить пропуск');
    // TODO: fetch('...', { method: 'DELETE', ... })
}

function getAllPasses() {
    console.log('Получить все пропуски');
    // TODO: fetch('...', { method: 'GET', ... })
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

// ===== ADMIN Actions =====



