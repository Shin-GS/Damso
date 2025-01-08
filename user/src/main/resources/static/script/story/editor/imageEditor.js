let filesQueue = [];
let currentFileIndex = 0;

async function initializeImageEditor() {
    const imageEditorContainer = document.querySelector('#image-editor-container');
    if (!imageEditorContainer) return;

    filesQueue = [];
    currentFileIndex = 0;
    new Dropzone("#upload-dropzone", {
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

            // 업로드 성공 시 정렬 섹션에 이미지 추가
            this.on("success", function (file, response) {
                if (response && response.result.url) {
                    addImageToSortableList(response.result.url);
                    this.removeFile(file);
                }
            });
        }
    });

    initializeSortable();
}

// 이미지 정렬 기능
function initializeSortable() {
    const sortableContainer = document.querySelector('#sortable-list');
    if (!sortableContainer) {
        return;
    }

    new Sortable(sortableContainer, {
        animation: 150,
        ghostClass: 'bg-gray-100',
        onEnd: () => {
            console.log('이미지 순서 변경 완료');
        }
    });
}

// 이미지 편집 모달 표시 및 Cropper 초기화
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

// 업로드된 이미지 리스트에 추가
function addImageToSortableList(imageUrl) {
    const sortableContainer = document.querySelector('#sortable-list');
    if (!sortableContainer) return;

    const imageDiv = document.createElement('div');
    imageDiv.classList.add('p-2', 'bg-white', 'shadow', 'rounded', 'relative');

    imageDiv.innerHTML = `<img src="${imageUrl}" class="w-full rounded" alt="">
        <button type="button" 
            id="remove-image-button" 
            class="absolute top-2 right-2 bg-red-500 text-white rounded-full w-6 h-6 flex items-center justify-center">
            ×
        </button>
    `;
    sortableContainer.appendChild(imageDiv);
}

// 이미지 제거 이벤트 핸들러
document.addEventListener('click', (event) => {
    if (event.target.matches('.remove-image-button')) {
        const imageDiv = event.target.closest('div');
        if (imageDiv) {
            imageDiv.remove();
        }
    }
});

// 편집 적용 및 건너뛰기
document.addEventListener('click', (event) => {
    const modal = document.querySelector('#image-edit-modal');
    if (!modal) return;

    const cropperImage = modal.querySelector('#image-edit-modal-image');
    const dropzone = Dropzone.forElement("#upload-dropzone");

    if (event.target.matches('#apply-edit-button')) {
        const canvas = cropperImage.cropper.getCroppedCanvas();
        canvas.toBlob(blob => {
            const originalFile = filesQueue[currentFileIndex];
            dropzone.removeFile(originalFile);

            const newFile = new File([blob], originalFile.name, {type: 'image/jpeg'});
            newFile.upload = {file: newFile};

            dropzone.addFile(newFile);
            dropzone.processFile(newFile);

            modal.classList.add('hidden');
            processNextFile();
        });
    }

    if (event.target.matches('#skip-edit-button')) {
        dropzone.processFile(filesQueue[currentFileIndex]);
        modal.classList.add('hidden');
        processNextFile();
    }

    if (event.target.matches('#close-modal-button')) {
        filesQueue.forEach(file => dropzone.removeFile(file));
        filesQueue = [];
        modal.classList.add('hidden');
    }
});

// 다음 파일 처리
function processNextFile() {
    currentFileIndex++;
    if (currentFileIndex < filesQueue.length) {
        showEditModal(filesQueue[currentFileIndex]);
    } else {
        filesQueue = [];
        currentFileIndex = 0;
    }
}
