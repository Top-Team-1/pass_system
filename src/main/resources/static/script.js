document.addEventListener('DOMContentLoaded', () => {
    const loginForm = document.getElementById('loginForm');
    const registerForm = document.getElementById('registerForm');

    if (loginForm) {
        loginForm.addEventListener('submit', async (e) => {
            e.preventDefault();

            const data = {
                phone: loginForm.elements['phone'].value,
                password: loginForm.elements['password'].value
            };

            const response = await fetch('http://localhost:8080/auth/sign-in', {
                method: 'POST',
                headers: {'Content-Type': 'application/json'},
                body: JSON.stringify(data)
            });

            if (response.ok) {
                const result = await response.json();
                localStorage.setItem('jwt', result.token); // сохраняем токен
                alert('Успешный вход!');
                // тут будем отправлять на нужную страницу
            } else {
                const errorText = await response.text();
                console.error('Ошибка входа:', errorText);
                alert('Ошибка входа');
            }
        });
    }

    if (registerForm) {
        registerForm.addEventListener('submit', async (e) => {
            e.preventDefault();

            const data = {
                firstName: registerForm.elements['firstName'].value,
                lastName: registerForm.elements['lastName'].value,
                dateOfBirth: registerForm.elements['dateOfBirth'].value,
                phone: registerForm.elements['phone'].value,
                password: registerForm.elements['password'].value
            };

            const response = await fetch('http://localhost:8080/auth/sign-up', {
                method: 'POST',
                headers: {'Content-Type': 'application/json'},
                body: JSON.stringify(data)
            });

            if (response.ok) {
                const result = await response.json();
                localStorage.setItem('jwt', result.token); // сохраняем токен
                alert('Успешная регистрация!');
                location.href = 'index.html'; // возвращаем на вход
            } else {
                const errorText = await response.text();
                console.error('Ошибка регистрации:', errorText);
                alert('Ошибка регистрации');
            }
        });
    }
});