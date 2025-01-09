package com.damso.user.controller.upload;

import com.damso.auth.session.SessionMemberId;
import com.damso.core.response.success.SuccessCode;
import com.damso.core.response.success.SuccessResponse;
import com.damso.user.service.upload.ImageFileUploader;
import com.damso.user.service.upload.VideoFileUploader;
import com.damso.user.service.upload.model.FileUploadModel;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/upload")
@RequiredArgsConstructor
public class UploadApi {
    private final ImageFileUploader imageFileUploader;
    private final VideoFileUploader videoFileUploader;

    @PostMapping("/image")
    public SuccessResponse uploadImage(@RequestPart("file") MultipartFile image,
                                       @SessionMemberId Long memberId) {
        FileUploadModel model = imageFileUploader.upload(image, memberId);
        return SuccessResponse.of(SuccessCode.SUCCESS, model);
    }

    @PostMapping("/video")
    public SuccessResponse uploadVideo(@RequestPart("file") MultipartFile video,
                                       @SessionMemberId Long memberId) {
        FileUploadModel model = videoFileUploader.upload(video, memberId);
        return SuccessResponse.of(SuccessCode.SUCCESS, model);
    }
}
