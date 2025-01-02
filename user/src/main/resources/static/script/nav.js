document.addEventListener('click', function (event) {
    const userInfo = event.target.closest('.user-info');
    if (userInfo) {
        const submenu = userInfo.querySelector('.user-submenu');
        submenu.style.display = submenu.style.display === 'block' ? 'none' : 'block';
    }
});

function handleCreateContentsResponse(event) {
    const response = JSON.parse(event.detail.xhr.responseText);
    if (!event.detail.successful) {
        alert('오류가 발생했습니다: ' + response.message || "콘텐츠 작성 실패");
        return;
    }

    if (response.status >= 200 && response.status < 300) {
        window.location.href = `/contents/edit/${response.result.contentId}`;
    }
}

// 전역 스코프에 함수 등록
window.handleCreateContentsResponse = handleCreateContentsResponse;
