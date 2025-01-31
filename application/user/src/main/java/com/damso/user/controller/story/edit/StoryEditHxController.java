package com.damso.user.controller.story.edit;

import com.damso.common.auth.session.SessionMemberId;
import com.damso.common.request.ModelAndViewBuilder;
import com.damso.userservice.story.StoryEditor;
import com.damso.userservice.story.request.StoryUpdateCommentTypeRequest;
import com.damso.userservice.story.request.StoryUpdateTitleRequest;
import com.damso.userservice.upload.ImageFileUploader;
import com.damso.userservice.upload.VideoFileUploader;
import com.damso.userservice.upload.response.FileUploadResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/hx/stories/edit")
@RequiredArgsConstructor
public class StoryEditHxController {
    private final StoryEditor storyEditor;
    private final ImageFileUploader imageFileUploader;
    private final VideoFileUploader videoFileUploader;

    @PutMapping("/{storyId}/title")
    public List<ModelAndView> updateTitle(@PathVariable("storyId") Long storyId,
                                          @SessionMemberId Long memberId,
                                          @ModelAttribute @Valid StoryUpdateTitleRequest request) {
        storyEditor.updateTitle(storyId, memberId, request.title());
        return new ModelAndViewBuilder()
                .addFragment("templates/components/toast.html", "components/toast :: success", "message", "스토리 임시저장에 성공했습니다.")
                .build();
    }

    @PutMapping("/{storyId}/comment-type")
    public List<ModelAndView> updateCommentType(@PathVariable("storyId") Long storyId,
                                                @SessionMemberId Long memberId,
                                                @ModelAttribute @Valid StoryUpdateCommentTypeRequest request) {
        storyEditor.updateCommentType(storyId, memberId, request.commentType());
        return new ModelAndViewBuilder()
                .addFragment("templates/components/toast.html", "components/toast :: success", "message", "스토리 임시저장에 성공했습니다.")
                .build();
    }

    @DeleteMapping("/{storyId}/temporary")
    public List<ModelAndView> reset(@PathVariable("storyId") Long storyId,
                                    @SessionMemberId Long memberId) {
        storyEditor.reset(storyId, memberId);
        return new ModelAndViewBuilder()
                .addFragment("templates/components/toast.html", "components/toast :: success", "message", "스토리 초기화에 성공했습니다.")
                .build();
    }

    @PutMapping("/{storyId}/published")
    public List<ModelAndView> published(@PathVariable("storyId") Long storyId,
                                        @SessionMemberId Long memberId) {
        storyEditor.published(storyId, memberId);
        return new ModelAndViewBuilder()
                .addFragment("templates/components/toast.html", "components/toast :: success", "message", "스토리 반영에 성공했습니다.")
                .build();
    }

    @DeleteMapping("/{storyId}")
    public List<ModelAndView> delete(@PathVariable("storyId") Long storyId,
                                     @SessionMemberId Long memberId) {
        storyEditor.delete(storyId, memberId);
        return new ModelAndViewBuilder()
                .addFragment("templates/components/toast.html", "components/toast :: success", "message", "스토리 삭제에 성공했습니다.")
                .build();
    }

    @PostMapping("/upload/image")
    public List<ModelAndView> uploadImage(@RequestPart("file") MultipartFile image,
                                          @SessionMemberId Long memberId) {
        FileUploadResponse upload = imageFileUploader.upload(image, memberId);

        Map<String, Object> imageUploadData = new HashMap<>();
        imageUploadData.put("files", List.of(upload.url()));
        return new ModelAndViewBuilder()
                .addFragment("templates/components/story/edit/upload.html", "components/story/edit/upload :: story-upload-image", imageUploadData)
                .build();
    }

    @PostMapping("/upload/video")
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
