function toggleHiddenInput(checkbox) {
    const hiddenInput = document.getElementById(`hidden-${checkbox.id}`);
    if (!hiddenInput) {
        console.warn(`Hidden input not found for checkbox with id: ${checkbox.id}`);
        return;
    }

    if (checkbox.checked) {
        hiddenInput.setAttribute('name', '');
    } else {
        hiddenInput.setAttribute('name', 'published');
    }
}

document.addEventListener('DOMContentLoaded', () => {
    document.body.addEventListener('htmx:beforeRequest', (event) => {
        const input = event.target;
        const patternAttr = input.getAttribute('pattern');
        const errorElement = document.getElementById(input.id + '-error');
        if (!patternAttr || !errorElement) {
            return;
        }

        const pattern = new RegExp(patternAttr);
        if (!pattern.test(input.value)) {
            errorElement.classList.remove('hidden');
            input.classList.add('border-red-500');
            event.preventDefault();
        } else {
            errorElement.classList.add('hidden');
            input.classList.remove('border-red-500');
        }
    });

    document.body.addEventListener('htmx:afterRequest', (event) => {
        const toast = document.querySelector('#toast-success') || document.querySelector('#toast-error');
        if (toast) {
            setTimeout(() => {
                toast.classList.add('opacity-0');
                setTimeout(() => toast.remove(), 300);
            }, 3000);
        }
    });
});

// 전역 스코프에 함수 등록
window.toggleHiddenInput = toggleHiddenInput;
