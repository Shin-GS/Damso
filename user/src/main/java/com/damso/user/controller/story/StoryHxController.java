package com.damso.user.controller.story;

import com.damso.auth.session.SessionMemberId;
import com.damso.core.enums.story.StoryType;
import com.damso.user.service.story.StoryEditor;
import com.damso.user.service.story.StoryFinder;
import com.damso.user.service.story.request.StoryEditTitleRequest;
import com.damso.user.service.story.response.StoryEditInfoResponse;
import com.damso.user.service.upload.ImageFileUploader;
import com.damso.user.service.upload.VideoFileUploader;
import com.damso.user.service.upload.response.FileUploadResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequestMapping("/hx/stories")
@RequiredArgsConstructor
public class StoryHxController {
    private final StoryFinder storyFinder;
    private final StoryEditor storyEditor;
    private final ImageFileUploader imageFileUploader;
    private final VideoFileUploader videoFileUploader;

    @GetMapping("/{storyId}/edit")
    public String getStory(@PathVariable("storyId") Long storyId,
                           @SessionMemberId Long memberId,
                           @RequestParam(value = "storyType", required = false, defaultValue = "TEXT") StoryType storyType,
                           Model model) {
        StoryEditInfoResponse editInfoResponse = storyFinder.getEditInfo(storyId, memberId);
        model.addAttribute("story", editInfoResponse);
        model.addAttribute("images", editInfoResponse.images());
        model.addAttribute("video", editInfoResponse.video());

        String fragment = switch (storyType) {
            case TEXT -> " :: text-editor";
            case IMAGE -> " :: image-editor";
            case VIDEO -> " :: video-editor";
        };
        return "components/story/edit/editor" + fragment;
    }

    @PostMapping("/{storyId}/edit/title")
    public String saveTitle(@PathVariable("storyId") Long storyId,
                            @SessionMemberId Long memberId,
                            @RequestBody @Valid StoryEditTitleRequest request,
                            Model model) {
        storyEditor.updateTitle(storyId, memberId, request.title());

        String fragment = " :: success"; // todo exception handler에 error시 처리 추가 필요
        return "components/story/edit/toast" + fragment;
    }

    @PostMapping("/upload/image")
    public String uploadImage(@RequestPart("file") MultipartFile image,
                              @SessionMemberId Long memberId,
                              Model model) {
        FileUploadResponse uploadResponse = imageFileUploader.upload(image, memberId);
        model.addAttribute("images", List.of(uploadResponse.url()));

        String fragment = " :: story-upload-image";
        return "components/story/storyUploadFile" + fragment;
    }

    @PostMapping("/upload/video")
    public String uploadVideo(@RequestPart("file") MultipartFile video,
                              @SessionMemberId Long memberId,
                              Model model) {
        FileUploadResponse uploadResponse = videoFileUploader.upload(video, memberId);
        model.addAttribute("video", List.of(uploadResponse.url()));

        String fragment = " :: story-upload-video";
        return "components/story/storyUploadFile" + fragment;
    }
}
