let filesQueue = [];
let currentFileIndex = 0;

// 초기화
async function initializeImageEditor() {
    if (!getElement('#image-editor-container')) return;

    resetQueue();
    initializeDropzone();
    initializeSortable();
}

// DOM 요소 가져오기
function getElement(selector) {
    return document.querySelector(selector);
}

// 큐 초기화
function resetQueue() {
    filesQueue = [];
    currentFileIndex = 0;
}

// Dropzone 초기화
function initializeDropzone() {
    new Dropzone("#upload-dropzone", {
        url: '/hx/stories/upload/image',
        autoProcessQueue: false,
        acceptedFiles: 'image/*',
        init: function () {
            this.on("addedfile", handleFileAdded);
            this.on("success", handleUploadSuccess);
        }
    });
}

// 파일 추가 처리
function handleFileAdded(file) {
    filesQueue.push(file);
    if (filesQueue.length === 1) {
        showEditModal(file);
    }
}

// 편집 모달 열기
function showEditModal(file) {
    const reader = new FileReader();
    reader.onload = (e) => {
        const modal = getElement('#image-edit-modal');
        const image = modal.querySelector('#image-edit-modal-image');
        image.src = e.target.result;

        modal.classList.remove('hidden');
        initializeCropper(image);
    };
    reader.readAsDataURL(file);
}

// 업로드 성공 처리
function handleUploadSuccess(file, response) {
    if (response) {
        addImageToSortableList(response.text());
        removeFileFromDropzone(file);
    }
}

// Cropper 초기화
function initializeCropper(image) {
    if (image.cropper) {
        image.cropper.destroy();
    }
    new Cropper(image, {
        aspectRatio: 1,
        autoCropArea: 0.8,
        viewMode: 1
    });
}

// 이미지 목록에 추가
function addImageToSortableList(imageHtml) {
    const container = getElement('#sortable-list');
    if (!container) return;
    container.insertAdjacentHTML('beforeend', imageHtml);
}

// Dropzone에서 파일 제거
function removeFileFromDropzone(file) {
    Dropzone.forElement("#upload-dropzone").removeFile(file);
}

// 정렬 초기화
function initializeSortable() {
    const container = getElement('#sortable-list');
    if (!container) return;

    new Sortable(container, {
        animation: 150,
        ghostClass: 'bg-gray-100',
        onEnd: () => console.log('이미지 순서 변경 완료')
    });
}

// 다음 파일 처리
function processNextFile() {
    if (filesQueue.length > 0) {
        showEditModal(filesQueue[0]);
    } else {
        resetQueue();
    }
}


// 파일 제거 이벤트 핸들러
document.addEventListener('click', (event) => {
    if (event.target.matches('.remove-image-button')) {
        event.target.closest('div')?.remove();
    }
});

// 편집 및 업로드 이벤트 핸들러
document.addEventListener('click', handleEditorActions);

function handleEditorActions(event) {
    const modal = getElement('#image-edit-modal');
    if (!modal) return;

    const cropperImage = modal.querySelector('#image-edit-modal-image');
    if (event.target.matches('#apply-edit-button')) {
        const canvas = cropperImage.cropper.getCroppedCanvas();
        canvas.toBlob(blob => {
            const originalFile = filesQueue[currentFileIndex];
            removeFileFromDropzone(originalFile);

            const newFile = new File([blob], originalFile.name, {type: 'image/jpeg'});
            handleFileUpload(newFile, modal);
        });
    }

    if (event.target.matches('#skip-edit-button')) {
        handleFileUpload(filesQueue[currentFileIndex], modal);
    }

    if (event.target.matches('#close-modal-button')) {
        filesQueue.forEach(removeFileFromDropzone);
        resetQueue();
        modal.classList.add('hidden');
    }
}

// 파일 업로드 (편집 파일 또는 원본 파일)
async function handleFileUpload(file, modal) {
    removeFileFromDropzone(file);

    const formData = new FormData();
    formData.append("file", file);

    try {
        const response = await fetch('/hx/stories/upload/image', {
            method: 'POST',
            body: formData
        });

        const imageHtml = await response.text();
        if (imageHtml) {
            addImageToSortableList(imageHtml);
        }

    } catch (error) {
        console.error('업로드 실패:', error);
        alert('이미지 업로드에 실패했습니다.');
        return null;
    }

    modal.classList.add('hidden');
    filesQueue.splice(currentFileIndex, 1);
    processNextFile();
}
