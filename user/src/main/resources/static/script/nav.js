import {fetchWithCredentials} from "./customFetch.js";

document.addEventListener('DOMContentLoaded', function () {
    const hamburgerMenu = document.querySelector('.hamburger-menu');
    const navMenu = document.querySelector('.nav-menu');
    const loginButton = document.getElementById('login-button');
    const userInfo = document.getElementById('user-info');
    const profilePicture = document.getElementById('profile-picture');
    const userName = document.getElementById('user-name');
    const userSubmenu = userInfo.querySelector('.user-submenu');

    hamburgerMenu.addEventListener('click', function () {
        navMenu.classList.toggle('active');
    });

    userInfo.addEventListener('click', function () {
        userSubmenu.style.display = userSubmenu.style.display === 'block' ? 'none' : 'block';
    });

    function fetchToken() {
        const urlParams = new URLSearchParams(window.location.search);

        // URL을 통해 auth 토큰 세팅
        const auth = urlParams.get('auth');
        if (auth) {
            localStorage.setItem('auth', auth);
        }

        // URL을 통해 refresh 토큰 세팅
        const refresh = urlParams.get('refresh');
        if (refresh) {
            localStorage.setItem('refresh', refresh);
        }

        // URL에서 토큰 정보 제거
        const cleanUrl = window.location.origin + window.location.pathname;
        window.history.replaceState({}, document.title, cleanUrl);
    }

    // API를 통해 회원 정보 세팅
    function fetchMemberInfo() {
        const authToken = localStorage.getItem("auth");
        if (!authToken) {
            return;
        }

        fetchWithCredentials('/api/member/refresh-info')
            .then(response => {
                if (!response.ok) {
                    throw new Error('Failed to fetch member info');
                }
                return response.json();
            })
            .then(data => {
                console.log('Member Info:', data);

                // 로그인 버튼 숨기기
                loginButton.style.display = 'none';

                // 사용자 정보 표시
                userInfo.style.display = 'flex';
                // profilePicture.src = data.profilePicture || '/default-profile.png';
                profilePicture.src = '/favicon.ico';
                userName.textContent = data.name || '회원님';
            })
            .catch(error => {
                console.error('Error fetching member info:', error);
            });
    }

    fetchToken();
    fetchMemberInfo();
});
