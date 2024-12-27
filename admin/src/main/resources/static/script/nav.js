document.addEventListener('DOMContentLoaded', function () {
    const loginButton = document.getElementById('login-button');
    const userInfo = document.getElementById('user-info');
    const profilePicture = document.getElementById('profile-picture');
    const userName = document.getElementById('user-name');
    const userSubmenu = userInfo.querySelector('.user-submenu');

    // 사용자 정보 드롭다운 메뉴 토글
    userInfo.addEventListener('click', function () {
        userSubmenu.style.display = userSubmenu.style.display === 'block' ? 'none' : 'block';
    });

    // API 호출로 사용자 정보 가져오기
    function fetchRefreshInfo() {
        fetch('/api/auth/refresh-info')
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

    // 로그아웃 처리
    function handleLogout() {
        const authToken = localStorage.getItem('auth');
        if (!authToken) {
            window.location.href = '/';
            return;
        }

        fetch("/api/auth/logout", {
            method: "POST",
            headers: {"Content-Type": "application/json"}
        })
            .then(response => {
                if (!response.ok) {
                    return response.json().then(error => {
                        return Promise.reject(new Error(error.message || "로그아웃 실패"));
                    });
                }
                return response.json();
            })
            .then(() => {
                window.location.href = '/'; // 홈 화면으로 이동
            })
            .catch((error) => {
                console.error('Error during logout:', error);
                window.location.href = '/';
            });
    }

    // 로그아웃 버튼 이벤트 추가
    const logoutButton = userSubmenu.querySelector('#logout');
    if (logoutButton) {
        logoutButton.addEventListener('click', function (event) {
            event.preventDefault(); // 기본 동작 막기
            handleLogout();
        });
    }

    // 초기화 함수 호출8ip'
    fetchRefreshInfo();
});
