package com.damso.user.controller.story;

import com.damso.auth.session.SessionMemberId;
import com.damso.core.response.ModelAndViewBuilder;
import com.damso.user.service.upload.ImageFileUploader;
import com.damso.user.service.upload.VideoFileUploader;
import com.damso.user.service.upload.response.FileUploadResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/hx/stories")
@RequiredArgsConstructor
public class StoryUploadHxController {
    private final ImageFileUploader imageFileUploader;
    private final VideoFileUploader videoFileUploader;

    @PostMapping("/upload/image")
    public List<ModelAndView> uploadImage(@RequestPart("file") MultipartFile image,
                                          @SessionMemberId Long memberId) {
        FileUploadResponse uploadResponse = imageFileUploader.upload(image, memberId);

        Map<String, Object> imageUploadData = new HashMap<>();
        imageUploadData.put("files", List.of(uploadResponse.url()));
        return new ModelAndViewBuilder()
                .addFragment("templates/components/story/edit/uploadFile.html", "components/story/edit/uploadFile :: story-upload-image", imageUploadData)
                .build();
    }

    @PostMapping("/upload/video")
    public List<ModelAndView> uploadVideo(@RequestPart("file") MultipartFile video,
                                          @SessionMemberId Long memberId) {
        FileUploadResponse uploadResponse = videoFileUploader.upload(video, memberId);

        Map<String, Object> videoUploadData = new HashMap<>();
        videoUploadData.put("files", List.of(uploadResponse.url()));
        return new ModelAndViewBuilder()
                .addFragment("templates/components/story/edit/uploadFile.html", "components/story/edit/uploadFile :: story-upload-video", videoUploadData)
                .build();
    }
}
