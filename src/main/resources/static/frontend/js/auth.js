
const loginForm = document.getElementById('loginForm');

if (loginForm) {
    loginForm.addEventListener('submit', (e) => {
        e.preventDefault();

        const email = document.getElementById('email').value;
        const password = document.getElementById('password').value;

        if (!email || !password) {
            alert("Enter credentials");
            return;
        }

       
        const token = btoa(email + ":" + password);

        localStorage.setItem('authToken', token);
        localStorage.setItem('userEmail', email);

        
        window.location.href = 'dashboard.html';
    });
}


function checkAuth() {

    const token = localStorage.getItem('authToken');
    const page = window.location.pathname.split('/').pop();

    
    if (!token && page !== 'adminlog.html' && page !== '') {
        window.location.href = 'adminlog.html';
    }

    
}



function logout() {

    localStorage.removeItem('authToken');
    localStorage.removeItem('userEmail');

    window.location.href = 'adminlog.html';
}


document.addEventListener('DOMContentLoaded', checkAuth);