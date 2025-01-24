package com.damso.user.controller.upload;

import com.damso.common.auth.session.SessionMemberId;
import com.damso.core.code.SuccessCode;
import com.damso.common.response.SuccessResponse;
import com.damso.userservice.upload.ImageFileUploader;
import com.damso.userservice.upload.VideoFileUploader;
import com.damso.userservice.upload.response.FileUploadResponse;
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
        FileUploadResponse uploadResponse = imageFileUploader.upload(image, memberId);
        return SuccessResponse.of(SuccessCode.SUCCESS, uploadResponse);
    }

    @PostMapping("/video")
    public SuccessResponse uploadVideo(@RequestPart("file") MultipartFile video,
                                       @SessionMemberId Long memberId) {
        FileUploadResponse uploadResponse = videoFileUploader.upload(video, memberId);
        return SuccessResponse.of(SuccessCode.SUCCESS, uploadResponse);
    }
}
