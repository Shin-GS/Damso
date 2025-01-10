async function initializeVideoEditor() {
    const uploadButtonContainer = document.querySelector('#video-upload-button-container');
    const progressContainer = document.querySelector('#upload-progress');
    const uploadedVideosContainer = document.querySelector('#uploaded-videos');
    if (!uploadButtonContainer || !progressContainer || !uploadedVideosContainer) return;

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
                updateProgress('No file selected.', progressContainer);
                return;
            }

            if (event.target.files.length > 1) {
                updateProgress('You can upload only one video at a time.', progressContainer);
                return;
            }

            // 파일 업로드 처리
            updateProgress(`Uploading ${file.name}...`, progressContainer);
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
                    updateProgress('Upload completed successfully!', progressContainer);
                })
                .catch((error) => {
                    console.error(error);
                    updateProgress(`Upload failed: ${error.message}`, progressContainer);
                })
                .finally(() => {
                    event.target.value = '';
                });
        }
    });
}

// 진행 상태 업데이트 함수
function updateProgress(message, container) {
    if (container) {
        container.innerHTML = message;
    }
}
