import { fetchWithCredentials } from "./customFetch.js";

document.addEventListener('DOMContentLoaded', function () {
    const navMenu = document.querySelector('.nav-menu');
    const loginButton = document.getElementById('login-button');
    const userInfo = document.getElementById('user-info');
    const profilePicture = document.getElementById('profile-picture');
    const userName = document.getElementById('user-name');
    const userSubmenu = userInfo.querySelector('.user-submenu');

    // 사용자 정보 드롭다운 메뉴 토글
    userInfo.addEventListener('click', function () {
        userSubmenu.style.display = userSubmenu.style.display === 'block' ? 'none' : 'block';
    });

    // URL에서 auth 및 refresh 토큰 가져오기 및 저장
    function fetchToken() {
        const urlParams = new URLSearchParams(window.location.search);

        // auth 토큰 저장
        const auth = urlParams.get('auth');
        if (auth) {
            localStorage.setItem('auth', auth);
        }

        // refresh 토큰 저장
        const refresh = urlParams.get('refresh');
        if (refresh) {
            localStorage.setItem('refresh', refresh);
        }

        // URL 정리
        const cleanUrl = window.location.origin + window.location.pathname;
        window.history.replaceState({}, document.title, cleanUrl);
    }

    // API 호출로 사용자 정보 가져오기
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
                profilePicture.src = data.profilePicture || '/favicon.ico';
                userName.textContent = data.name || '회원님';
            })
            .catch(error => {
                console.error('Error fetching member info:', error);
            });
    }

    // 초기화 함수 호출
    fetchToken();
    fetchMemberInfo();
});
