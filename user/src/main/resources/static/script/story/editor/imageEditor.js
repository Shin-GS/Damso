// 이미지 에디터 초기화
let filesQueue = [];
let currentFileIndex = 0;

async function initializeImageEditor() {
    const imageEditorContainer = document.querySelector('#image-editor-container');
    if (!imageEditorContainer) {
        return;
    }

    new Dropzone("#uploadDropzone", {
        url: '/api/upload/image',
        autoProcessQueue: false,
        acceptedFiles: 'image/*',
        init: function () {
            this.on("addedfile", (file) => {
                filesQueue.push(file);
                if (filesQueue.length === 1) {
                    showEditModal(file);
                }
            });

            // 업로드 성공 후 이미지 정렬 섹션으로 이동
            this.on("success", function (file, response) {
                if (response && response.result.url) {
                    addImageToSortableList(response.result.url);
                    this.removeFile(file);  // 업로드 완료된 파일은 Dropzone에서 제거
                }
            });
        }
    });
}

// 정렬 섹션에 이미지 추가
function addImageToSortableList(imageUrl) {
    const sortableContainer = document.querySelector('.sortable-list');

    // 이미지와 버튼을 담을 div 생성
    const imageDiv = document.createElement('div');
    imageDiv.classList.add('p-2', 'bg-white', 'shadow', 'rounded', 'relative');

    // 이미지 요소 추가
    imageDiv.innerHTML = `
            <img src="${imageUrl}" class="w-full rounded">
            <button type="button" class="remove-image-button absolute top-2 right-2 bg-red-500 text-white rounded-full w-6 h-6 flex items-center justify-center">
                ×
            </button>
        `;

    // 이미지 컨테이너 추가
    sortableContainer.appendChild(imageDiv);
}

// 이미지 제거 이벤트 위임
document.addEventListener('click', (event) => {
    if (event.target.matches('.remove-image-button')) {
        const imageDiv = event.target.closest('div');  // 가장 가까운 div 찾기
        if (imageDiv) {
            imageDiv.remove();
        }
    }
});

// 편집 모달 표시 및 Cropper 초기화
function showEditModal(file) {
    const reader = new FileReader();
    reader.onload = (e) => {
        const modal = document.querySelector('#image-edit-modal');
        const image = modal.querySelector('#image-edit-modal-image');
        image.src = e.target.result;

        modal.classList.remove('hidden');

        if (image.cropper) {
            image.cropper.destroy();
        }
        new Cropper(image, {
            aspectRatio: 1,
            autoCropArea: 0.8,
            viewMode: 1
        });
    };
    reader.readAsDataURL(file);
}

// 다음 파일 편집 진행
function processNextFile() {
    currentFileIndex++;
    if (currentFileIndex < filesQueue.length) {
        showEditModal(filesQueue[currentFileIndex]);
    } else {
        filesQueue = [];
        currentFileIndex = 0;
    }
}

// 이벤트 위임 - 편집 적용 및 건너뛰기
document.addEventListener('click', (event) => {
    const modal = document.querySelector('#image-edit-modal');
    if (!modal) {
        return;
    }

    const cropperImage = modal.querySelector('#image-edit-modal-image');
    const dropzone = Dropzone.forElement("#uploadDropzone");

    // 편집 적용
    if (event.target.matches('.apply-edit-button')) {
        const canvas = cropperImage.cropper.getCroppedCanvas();
        canvas.toBlob(blob => {
            const originalFile = filesQueue[currentFileIndex];
            dropzone.removeFile(originalFile);  // 기존 파일 제거

            // 새로운 파일 생성 및 추가
            const newFile = new File([blob], originalFile.name, {type: 'image/jpeg'});
            newFile.upload = {file: newFile};  // Dropzone에서 업로드 대상 파일 지정

            dropzone.addFile(newFile);  // 새 파일 추가
            dropzone.processFile(newFile);  // 즉시 업로드

            modal.classList.add('hidden');  // 모달 숨기기
            processNextFile();  // 다음 파일 처리
        });
    }

    // 편집 건너뛰기
    if (event.target.matches('.skip-edit-button')) {
        dropzone.processFile(filesQueue[currentFileIndex]);  // 업로드 즉시 실행
        modal.classList.add('hidden');  // 모달 숨기기
        processNextFile();  // 다음 파일 처리
    }

    // 편집창 닫기
    if (event.target.matches('.close-modal-button')) {
        // filesQueue에 남아 있는 모든 파일 제거
        filesQueue.forEach(file => {
            dropzone.removeFile(file);
        });

        // 대기열 초기화
        filesQueue = [];
        currentFileIndex = 0;

        // 모달 숨기기
        modal.classList.add('hidden');
    }
});
