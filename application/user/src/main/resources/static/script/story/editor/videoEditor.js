function initializeVideoEditor() {
    const uploadContainer = document.querySelector('#video-upload-container');
    const uploadedVideosContainer = document.querySelector('#uploaded-videos');
    const uploadButton = document.querySelector('#video-upload-button');
    if (!uploadContainer || !uploadedVideosContainer || !uploadButton) return;

    document.body.addEventListener('click', (event) => {
        if (event.target.matches('#remove-video-button')) {
            event.preventDefault();
            event.target.closest('div')?.remove();
        }

        if (event.target.id === 'video-upload-button') {
            event.preventDefault();
            const videoInput = document.querySelector('#video-input');
            if (videoInput) {
                videoInput.click();
            }
        }
    });

    document.body.addEventListener('change', (event) => {
        if (event.target.id === 'video-input' && !uploadButton.disabled) {
            const file = event.target.files[0];
            if (!file) {
                alert("동영상을 선택해주세요");
                return;
            }

            if (event.target.files.length > 1) {
                alert("한 번에 한 개의 동영상만 업로드 가능합니다.");
                return;
            }

            // 파일 크기 및 형식 검증
            const MAX_FILE_SIZE = 500 * 1024 * 1024;
            if (file.size > MAX_FILE_SIZE) {
                alert("최대 500MB 용량의 파일을 업로드 할 수 있습니다.");
                return;
            }

            const allowedFormats = ['video/mp4', 'video/quicktime', 'video/x-msvideo', 'video/x-matroska', 'video/x-ms-wmv', 'video/x-flv'];
            const allowedExtensions = ['mp4', 'mov', 'avi', 'mkv', 'wmv', 'flv'];
            const fileExtension = file.name.split('.').pop().toLowerCase();
            if (!allowedFormats.includes(file.type) && !allowedExtensions.includes(fileExtension)) {
                alert("지원되지 않는 파일 형식입니다.");
                return;
            }

            uploadButton.disabled = true;
            const formData = new FormData();
            formData.append('file', file);

            fetch('/hx/stories/upload/video', {
                method: 'POST',
                body: formData,
                signal: new AbortController().signal, // 연결 시간 초과 방지
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
                    console.error("업로드 중 오류 발생:", error);
                    alert("동영상 업로드에 실패했습니다. 다시 시도해주세요.");
                })
                .finally(() => {
                    uploadButton.disabled = false; // 업로드 버튼 활성화
                    event.target.value = ''; // 파일 입력 초기화
                });
        }
    });
}
