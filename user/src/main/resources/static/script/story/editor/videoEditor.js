// 비디오 에디터 초기화
async function initializeVideoEditor() {
    const videoEditorContainer = document.querySelector('#video-editor-container');
    if (!videoEditorContainer) {
        return;
    }

    const uppy = new window.Uppy.Uppy({  // window.Uppy로 변경
        restrictions: {
            maxNumberOfFiles: 1,
            maxFileSize: 500 * 1024 * 1024,  // 500MB 제한
            allowedFileTypes: ['.mp4', '.mov', '.avi', '.mkv', '.wmv', '.flv']
        }
    })
        .use(Uppy.Dashboard, {
            inline: true,
            target: '#video-editor-container',
            proudlyDisplayPoweredByUppy: false,
            height: 300
        })
        .use(Uppy.XHRUpload, {
            endpoint: '/api/upload/video',
            fieldName: 'file'
        });

    // 업로드 제한 실패 시 경고
    uppy.on('restriction-failed', () => {
        alert('동영상은 하나만 업로드할 수 있습니다.');
    });
}
