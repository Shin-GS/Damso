import {fetchWithCredentials} from "./customFetch.js";

document.addEventListener('DOMContentLoaded', function () {
    const hamburgerMenu = document.querySelector('.hamburger-menu');
    const navMenu = document.querySelector('.nav-menu');

    hamburgerMenu.addEventListener('click', function () {
        navMenu.classList.toggle('active');
    });

    //auth 토큰 세팅
    const urlParams = new URLSearchParams(window.location.search);
    const auth = urlParams.get('auth');
    if (auth) {
        localStorage.setItem('auth', auth);

        // URL에서 토큰 정보 제거
        const cleanUrl = window.location.origin + window.location.pathname;
        window.history.replaceState({}, document.title, cleanUrl);
    }

    //회원정보 세팅
    fetchWithCredentials('/api/member/info')
        .then(response => response.json())
        .then(data => {
            console.log('Member Info:', data);
        })
        .catch(error => {
            console.error('Error fetching member info:', error);
        });
});
