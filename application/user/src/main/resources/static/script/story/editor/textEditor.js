async function initializeTextEditor() {
    const textEditorContainer = document.querySelector('#text-editor-container');
    if (!textEditorContainer || textEditorContainer.__quill) return;

    const quill = new Quill(textEditorContainer, {
        theme: 'snow',
        modules: {
            toolbar: [
                ['bold', 'italic', 'underline'],
                [{'header': [1, 2, 3, false]}],
                [{'list': 'ordered'}, {'list': 'bullet'}],
                [{'align': []}],
                ['image', 'link'],
                ['clean'],
                ['tip']
            ]
        }
    });
    textEditorContainer.__quill = quill;

    const initialText = document.querySelector('#initial-text').value;
    quill.root.innerHTML = decodeHtmlEntities(initialText);

    quill.getModule('toolbar').addHandler('image', () => {
        const input = document.createElement('input');
        input.setAttribute('type', 'file');
        input.setAttribute('accept', '.png,.jpg,.jpeg,.gif,.bmp,.webp');
        input.click();

        input.onchange = async () => {
            const file = input.files[0];
            if (file) {
                const loadingIndicator = document.createElement('div');
                loadingIndicator.textContent = '이미지 업로드 중...';
                document.body.appendChild(loadingIndicator);

                const formData = new FormData();
                formData.append('file', file);

                try {
                    const response = await fetch('/api/upload/image', {
                        method: 'POST',
                        body: formData
                    });

                    const data = await response.json();
                    document.body.removeChild(loadingIndicator);

                    if (data && data.result && data.result.url) {
                        const range = quill.getSelection();
                        quill.insertEmbed(range.index, 'image', data.result.url);
                    } else {
                        alert('이미지 업로드 실패');
                    }
                } catch (error) {
                    document.body.removeChild(loadingIndicator);
                    alert('이미지 업로드 도중 오류 발생');
                }
            }
        };
    });

    addCustomKeyboardShortcuts(quill);
    addShortcutIcon();

    quill.focus();

    document.getElementById('shortcut-icon').addEventListener('click', () => {
        document.getElementById('shortcut-modal').classList.remove('hidden');
    });

    document.getElementById('modal-close-btn').addEventListener('click', () => {
        document.getElementById('shortcut-modal').classList.add('hidden');
    });

    window.addEventListener('click', (e) => {
        if (e.target === document.getElementById('shortcut-modal')) {
            document.getElementById('shortcut-modal').classList.add('hidden');
        }
    });
}

function decodeHtmlEntities(text) {
    const textarea = document.createElement('textarea');
    textarea.innerHTML = text;
    return textarea.value;
}

function addShortcutIcon() {
    const toolbar = document.querySelector('.ql-toolbar');
    if (document.querySelector('#shortcut-icon')) return;

    const shortcutIcon = document.createElement('span');
    shortcutIcon.id = 'shortcut-icon';
    shortcutIcon.classList.add('ql-formats');

    const button = document.createElement('button');
    button.type = 'button';
    button.classList.add('ql-tip');

    button.innerHTML = `<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" width="24" height="24">
            <path fill="none" d="M0 0h24v24H0z"/>
            <path d="M12 2C6.48 2 2 6.48 2 12s4.48 10 10 10 10-4.48 10-10S17.52 2 12 2zm0 18c-4.41 0-8-3.59-8-8s3.59-8 8-8 8 3.59 8 8-3.59 8-8 8zm0-12c-1.1 0-2 .9-2 2s.9 2 2 2 2-.9 2-2-.9-2-2-2zm0 12c-2.21 0-4-1.79-4-4h2c0 1.1.9 2 2 2s2-.9 2-2h2c0 2.21-1.79 4-4 4z"/>
        </svg>`;

    shortcutIcon.appendChild(button);
    toolbar.appendChild(shortcutIcon);
}

function addCustomKeyboardShortcuts(quill) {
    quill.keyboard.addBinding({key: 'B', shortKey: true}, () => {
        quill.format('bold', !quill.getFormat().bold);
    });

    quill.keyboard.addBinding({key: 'I', shortKey: true}, () => {
        quill.format('italic', !quill.getFormat().italic);
    });

    quill.keyboard.addBinding({key: 'U', shortKey: true}, () => {
        quill.format('underline', !quill.getFormat().underline);
    });

    quill.keyboard.addBinding({key: 'Z', shortKey: true}, () => {
        quill.history.undo();
    });

    quill.keyboard.addBinding({key: 'Z', shiftKey: true, shortKey: true}, () => {
        quill.history.redo();
    });

    quill.keyboard.addBinding({key: 'K', shortKey: true}, () => {
        const selection = quill.getSelection();
        const range = selection ? selection.index : 0;
        const currentUrl = quill.getText(range, range + 100).trim();
        const url = prompt('링크를 입력하세요:', currentUrl);

        if (url) {
            quill.formatText(range, range + currentUrl.length, 'link', url);
        }
    });

    quill.keyboard.addBinding({key: 'L', shortKey: true, shiftKey: true}, () => {
        quill.format('list', 'ordered');
    });

    quill.keyboard.addBinding({key: 'U', shortKey: true, shiftKey: true}, () => {
        quill.format('list', 'bullet');
    });

    quill.keyboard.addBinding({key: '1', shortKey: true, altKey: true}, () => {
        quill.format('header', 1);
    });

    quill.keyboard.addBinding({key: '2', shortKey: true, altKey: true}, () => {
        quill.format('header', 2);
    });

    quill.keyboard.addBinding({key: '3', shortKey: true, altKey: true}, () => {
        quill.format('header', 3);
    });
}
