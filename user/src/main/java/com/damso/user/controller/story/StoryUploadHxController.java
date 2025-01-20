package com.damso.user.controller.story;

import com.damso.auth.session.SessionMemberId;
import com.damso.user.service.upload.ImageFileUploader;
import com.damso.user.service.upload.VideoFileUploader;
import com.damso.user.service.upload.response.FileUploadResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequestMapping("/hx/stories")
@RequiredArgsConstructor
public class StoryUploadHxController {
    private final ImageFileUploader imageFileUploader;
    private final VideoFileUploader videoFileUploader;

    @PostMapping("/upload/image")
    public String uploadImage(@RequestPart("file") MultipartFile image,
                              @SessionMemberId Long memberId,
                              Model model) {
        FileUploadResponse uploadResponse = imageFileUploader.upload(image, memberId);
        model.addAttribute("images", List.of(uploadResponse.url()));

        String fragment = " :: story-upload-image";
        return "components/story/edit/uploadFile" + fragment;
    }

    @PostMapping("/upload/video")
    public String uploadVideo(@RequestPart("file") MultipartFile video,
                              @SessionMemberId Long memberId,
                              Model model) {
        FileUploadResponse uploadResponse = videoFileUploader.upload(video, memberId);
        model.addAttribute("video", List.of(uploadResponse.url()));

        String fragment = " :: story-upload-video";
        return "components/story/edit/uploadFile" + fragment;
    }
}
