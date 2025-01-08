async function initializeVideoEditor() {
    const videoEditorContainer = document.querySelector('#video-editor-container');
    if (!videoEditorContainer) return;

    const uppy = new window.Uppy.Uppy({
        restrictions: {
            maxNumberOfFiles: 1,
            maxFileSize: 500 * 1024 * 1024,
            allowedFileTypes: ['.mp4', '.mov', '.avi', '.mkv', '.wmv', '.flv']
        }
    })
        .use(Uppy.Dashboard, {
            inline: true,
            target: '#video-editor-container',
            height: 300,
            proudlyDisplayPoweredByUppy: false
        })
        .use(Uppy.XHRUpload, {
            endpoint: '/api/upload/video',
            fieldName: 'file'
        });

    uppy.on('restriction-failed', () => {
        alert('동영상은 하나만 업로드할 수 있습니다.');
    });
}
