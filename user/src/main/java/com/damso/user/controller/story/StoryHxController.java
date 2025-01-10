package com.damso.user.controller.story;

import com.damso.auth.session.SessionMemberId;
import com.damso.core.enums.story.StoryType;
import com.damso.user.service.story.StoryFinder;
import com.damso.user.service.story.model.StoryEditInfoModel;
import com.damso.user.service.upload.ImageFileUploader;
import com.damso.user.service.upload.VideoFileUploader;
import com.damso.user.service.upload.model.FileUploadModel;
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
    private final ImageFileUploader imageFileUploader;
    private final VideoFileUploader videoFileUploader;

    @GetMapping("/{storyId}/edit")
    public String getStory(@PathVariable("storyId") Long storyId,
                           @SessionMemberId Long memberId,
                           @RequestParam(value = "storyType", required = false, defaultValue = "TEXT") StoryType storyType,
                           Model model) {
        StoryEditInfoModel editInfoModel = storyFinder.getEditInfo(storyId, memberId);
        model.addAttribute("story", editInfoModel);
        model.addAttribute("images", editInfoModel.images());
        model.addAttribute("video", editInfoModel.video());

        String fragment = switch (storyType) {
            case TEXT -> " :: text-editor";
            case IMAGE -> " :: image-editor";
            case VIDEO -> " :: video-editor";
        };
        return "components/story/storyEditor" + fragment;
    }

    @PostMapping("/upload/image")
    public String uploadImage(@RequestPart("file") MultipartFile image,
                              @SessionMemberId Long memberId,
                              Model model) {
        FileUploadModel uploadModel = imageFileUploader.upload(image, memberId);
        model.addAttribute("images", List.of(uploadModel.url()));

        String fragment = " :: story-upload-image";
        return "components/story/storyUploadFile" + fragment;
    }

    @PostMapping("/upload/video")
    public String uploadVideo(@RequestPart("file") MultipartFile video,
                              @SessionMemberId Long memberId,
                              Model model) {
        FileUploadModel uploadModel = videoFileUploader.upload(video, memberId);
        model.addAttribute("video", List.of(uploadModel.url()));

        String fragment = " :: story-upload-video";
        return "components/story/storyUploadFile" + fragment;
    }
}
