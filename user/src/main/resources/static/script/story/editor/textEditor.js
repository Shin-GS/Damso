// 텍스트 에디터 초기화
async function initializeTextEditor() {
    const textEditorContainer = document.querySelector('#text-editor-container');
    if (!textEditorContainer) {
        return;
    }

    const quill = new Quill(textEditorContainer, {
        theme: 'snow',
        modules: {
            toolbar: [
                ['bold', 'italic', 'underline'],
                [{'header': [1, 2, 3, false]}],
                [{'list': 'ordered'}, {'list': 'bullet'}],
                [{'align': []}],
                ['image', 'link'],  // 사진 가능성 해상
                ['clean']
            ]
        }
    });

    // 이미지 업로드
    quill.getModule('toolbar').addHandler('image', () => {
        const input = document.createElement('input');
        input.setAttribute('type', 'file');
        input.setAttribute('accept', '.png,.jpg,.jpeg,.gif,.bmp,.webp');
        input.click();

        input.onchange = async () => {
            const file = input.files[0];
            if (file) {
                const formData = new FormData();
                formData.append('file', file);

                const res = await fetch('/api/upload/image', {
                    method: 'POST',
                    body: formData
                });

                const data = await res.json();
                if (data && data.result && data.result.url) {
                    const range = quill.getSelection();
                    quill.insertEmbed(range.index, 'image', data.result.url);

                } else {
                    alert('이미지 업로드 실패: 서버 응답에 URL이 없습니다.');
                }
            }
        };
    });
}
