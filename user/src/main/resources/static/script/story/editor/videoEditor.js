async function initializeVideoEditor() {
    const uploadButtonContainer = document.querySelector('#video-upload-button-container');
    const uploadedVideosContainer = document.querySelector('#uploaded-videos');
    if (!uploadButtonContainer || !uploadedVideosContainer) return;

    document.body.addEventListener('click', (event) => {
        if (event.target.matches('#remove-video-button')) {
            event.preventDefault();
            event.target.closest('div')?.remove();
        }

        if (event.target.id === 'upload-button') {
            event.preventDefault();

            const videoInput = document.querySelector('#video-input');
            if (videoInput) {
                videoInput.click();
            }
        }
    });

    document.body.addEventListener('change', async (event) => {
        // 파일 선택 처리
        if (event.target.id === 'video-input') {
            const file = event.target.files[0];
            if (!file) {
                alert("동영상을 선택해주세요");
                return;
            }

            if (event.target.files.length > 1) {
                return;
            }

            // 파일 업로드 처리
            const formData = new FormData();
            formData.append('file', file);

            fetch('/hx/stories/upload/video', {
                method: 'POST',
                body: formData,
            })
                .then((response) => {
                    if (!response.ok) {
                        throw new Error(`Upload failed with status ${response.status}`);
                    }
                    return response.text();
                })
                .then((videoHtml) => {
                    const tempDiv = document.createElement('div');
                    tempDiv.innerHTML = videoHtml.trim();
                    uploadedVideosContainer.replaceWith(tempDiv.firstChild);
                })
                .catch((error) => {
                    console.error(error);
                })
                .finally(() => {
                    event.target.value = '';
                });
        }
    });
}
