package com.damso.user.controller.upload;

import com.damso.auth.session.SessionMemberId;
import com.damso.core.response.success.SuccessCode;
import com.damso.core.response.success.SuccessResponse;
import com.damso.user.service.upload.FileUploader;
import com.damso.user.service.upload.model.FileUploadModel;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/upload")
@RequiredArgsConstructor
public class UploadApi {
    private final FileUploader fileUploader;

    @PostMapping("/{fileType}")
    public SuccessResponse uploadImage(@PathVariable("fileType") String fileType,
                                       @RequestPart("file") MultipartFile file,
                                       @SessionMemberId Long memberId) {
        FileUploadModel model = fileUploader.upload(fileType, file, memberId);
        return SuccessResponse.of(SuccessCode.SUCCESS, model);
    }
}
