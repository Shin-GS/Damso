package com.damso.user.controller.story;

import com.damso.auth.session.SessionMemberId;
import com.damso.core.enums.story.StoryType;
import com.damso.user.service.story.StoryEditor;
import com.damso.user.service.story.request.StoryUpdateCommentTypeRequest;
import com.damso.user.service.story.request.StoryUpdateTitleRequest;
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
    private final StoryEditor storyEditor;
    private final ImageFileUploader imageFileUploader;
    private final VideoFileUploader videoFileUploader;

    @GetMapping("/{storyId}/edit")
    public String getStory(@PathVariable("storyId") Long storyId,
                           @SessionMemberId Long memberId,
                           @RequestParam(value = "storyType", required = false, defaultValue = "TEXT") StoryType storyType,
                           Model model) {
        model.addAttribute("story", storyEditor.resolveTemporaryEditInfo(storyId, memberId));
//        model.addAttribute("images", editInfoResponse.images());
//        model.addAttribute("video", editInfoResponse.video());

        String fragment = switch (storyType) {
            case TEXT -> " :: text-editor";
            case IMAGE -> " :: image-editor";
            case VIDEO -> " :: video-editor";
        };
        return "components/story/edit/editor" + fragment;
    }

    @PutMapping("/{storyId}/title")
    public String updateTitle(@PathVariable("storyId") Long storyId,
                              @SessionMemberId Long memberId,
                              @ModelAttribute @Valid StoryUpdateTitleRequest request,
                              Model model) {
        storyEditor.updateTitle(storyId, memberId, request.title());
        model.addAttribute("message", "스토리 임시저장에 성공했습니다.");

        String fragment = " :: success";
        return "components/story/edit/toast" + fragment;
    }

    @PutMapping("/{storyId}/comment-type")
    public String updateCommentType(@PathVariable("storyId") Long storyId,
                                    @SessionMemberId Long memberId,
                                    @ModelAttribute @Valid StoryUpdateCommentTypeRequest request,
                                    Model model) {
        storyEditor.updateCommentType(storyId, memberId, request.commentType());
        model.addAttribute("message", "스토리 임시저장에 성공했습니다.");

        String fragment = " :: success";
        return "components/story/edit/toast" + fragment;
    }

    @DeleteMapping("/{storyId}/temporary")
    public String reset(@PathVariable("storyId") Long storyId,
                        @SessionMemberId Long memberId,
                        Model model) {
        storyEditor.reset(storyId, memberId);
        model.addAttribute("message", "스토리 초기화에 성공했습니다.");

        String fragment = " :: success";
        return "components/story/edit/toast" + fragment;
    }

    @PutMapping("/{storyId}/published")
    public String published(@PathVariable("storyId") Long storyId,
                            @SessionMemberId Long memberId,
                            Model model) {
        storyEditor.published(storyId, memberId);
        model.addAttribute("message", "스토리 반영에 성공했습니다.");

        String fragment = " :: success";
        return "components/story/edit/toast" + fragment;
    }

    @DeleteMapping("/{storyId}")
    public String delete(@PathVariable("storyId") Long storyId,
                         @SessionMemberId Long memberId,
                         Model model) {
        storyEditor.delete(storyId, memberId);
        model.addAttribute("message", "스토리 삭제에 성공했습니다.");

        String fragment = " :: success";
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
