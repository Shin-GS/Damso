// form 필드 → URLSearchParams 객체로 변환
function getFormParams(form) {
    return new URLSearchParams(new FormData(form));
}

// URL → form 상태 복원
function syncUrlToForm(form) {
    const params = new URLSearchParams(window.location.search);

    // keyword, sort
    form.keyword.value = params.get("keyword") || '';
    form.sort.value = params.get("sort") || 'latest';

    // category (multiple)
    const selectedCategories = params.getAll("category");
    for (const option of form.category.options) {
        option.selected = selectedCategories.includes(option.value);
    }
}

document.addEventListener("DOMContentLoaded", function () {
    const form = document.getElementById("filter-form");
    if (!form) return;

    // 1) 초기 로딩 시: 쿼리스트링 있으면 자동 요청
    if (window.location.search) {
        syncUrlToForm(form);
    }

    // 2) 검색 시: 주소창 URL 갱신
    form.addEventListener("htmx:afterRequest", function () {
        const params = getFormParams(form);
        const newUrl = `/stories?${params.toString()}`;
        window.history.pushState({}, '', newUrl);
    });

    // 3) 뒤로가기/앞으로가기 시 반영
    window.addEventListener("popstate", function () {
        syncUrlToForm(form);
    });
});
