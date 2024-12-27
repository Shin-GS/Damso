document.addEventListener('click', function (event) {
    const userInfo = event.target.closest('.user-info');
    if (userInfo) {
        const submenu = userInfo.querySelector('.user-submenu');
        submenu.style.display = submenu.style.display === 'block' ? 'none' : 'block';
    }
});
