package com.damso.user.controller.story;

import com.damso.common.auth.session.SessionMemberId;
import com.damso.common.request.ModelAndViewBuilder;
import com.damso.userservice.upload.ImageFileUploader;
import com.damso.userservice.upload.VideoFileUploader;
import com.damso.userservice.upload.response.FileUploadResponse;
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
@RequestMapping("/hx/stories/upload")
@RequiredArgsConstructor
public class StoryUploadHxController {
    private final ImageFileUploader imageFileUploader;
    private final VideoFileUploader videoFileUploader;

    @PostMapping("/image")
    public List<ModelAndView> uploadImage(@RequestPart("file") MultipartFile image,
                                          @SessionMemberId Long memberId) {
        FileUploadResponse upload = imageFileUploader.upload(image, memberId);

        Map<String, Object> imageUploadData = new HashMap<>();
        imageUploadData.put("files", List.of(upload.url()));
        return new ModelAndViewBuilder()
                .addFragment("templates/components/story/edit/upload.html", "components/story/edit/upload :: story-upload-image", imageUploadData)
                .build();
    }

    @PostMapping("/video")
    public List<ModelAndView> uploadVideo(@RequestPart("file") MultipartFile video,
                                          @SessionMemberId Long memberId) {
        FileUploadResponse upload = videoFileUploader.upload(video, memberId);

        Map<String, Object> videoUploadData = new HashMap<>();
        videoUploadData.put("files", List.of(upload.url()));
        return new ModelAndViewBuilder()
                .addFragment("templates/components/story/edit/upload.html", "components/story/edit/upload :: story-upload-video", videoUploadData)
                .build();
    }
}
