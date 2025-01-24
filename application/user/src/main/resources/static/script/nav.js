document.addEventListener('click', function (event) {
    const userInfo = event.target.closest('.user-info');
    if (userInfo) {
        const submenu = userInfo.querySelector('.user-submenu');
        submenu.style.display = submenu.style.display === 'block' ? 'none' : 'block';
    }
});

function handleCreateStoriesResponse(event) {
    const response = JSON.parse(event.detail.xhr.responseText);
    if (!event.detail.successful) {
        alert('오류가 발생했습니다: ' + response.message || "스토리 작성 실패");
        return;
    }

    if (response.status >= 200 && response.status < 300) {
        window.location.href = `/stories/${response.result.storyId}/edit`;
    }
}

// 전역 스코프에 함수 등록
window.handleCreateStoriesResponse = handleCreateStoriesResponse;
