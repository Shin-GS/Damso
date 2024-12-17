import {fetchWithCredentials} from "./customFetch.js";

document.addEventListener('DOMContentLoaded', function () {
    const hamburgerMenu = document.querySelector('.hamburger-menu');
    const navMenu = document.querySelector('.nav-menu');

    hamburgerMenu.addEventListener('click', function () {
        navMenu.classList.toggle('active');
    });

    function fetchToken() {
        const urlParams = new URLSearchParams(window.location.search);

        //url을 통해 auth 토큰 세팅
        const auth = urlParams.get('auth');
        if (auth) {
            localStorage.setItem('auth', auth);
        }

        //url을 통해 refresh 토큰 세팅
        const refresh = urlParams.get('refresh');
        if (refresh) {
            localStorage.setItem('refresh', refresh);
        }

        // URL에서 토큰 정보 제거
        const cleanUrl = window.location.origin + window.location.pathname;
        window.history.replaceState({}, document.title, cleanUrl);
    }

    //api를 통해 회원정보 세팅
    function fetchMemberInfo() {
        const authToken = localStorage.getItem("auth");
        if (!authToken) {
            return;
        }

        fetchWithCredentials('/api/member/info')
            .then(response => response.json())
            .then(data => {
                console.log('Member Info:', data);
            })
            .catch(error => {
                console.error('Error fetching member info:', error);
            });
    }

    fetchToken();
    fetchMemberInfo();
});
