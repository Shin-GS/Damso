package com.damso.user.controller.api.story;

import com.damso.common.auth.session.SessionMemberId;
import com.damso.common.response.SuccessResponse;
import com.damso.core.code.SuccessCode;
import com.damso.userservice.story.StoryEditor;
import com.damso.userservice.story.StoryPageEditor;
import com.damso.userservice.story.request.StoryPageReorderRequest;
import com.damso.userservice.story.request.StoryUpdateCommentTypeRequest;
import com.damso.userservice.story.request.StoryUpdateTitleRequest;
import com.damso.userservice.story.response.StoryEditResponse;
import com.damso.userservice.upload.ImageFileUploader;
import com.damso.userservice.upload.VideoFileUploader;
import com.damso.userservice.upload.response.FileUploadResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/stories/edit")
@RequiredArgsConstructor
public class StoryEditApi {
    private final StoryEditor storyEditor;
    private final StoryPageEditor storyPageEditor;
    private final ImageFileUploader imageFileUploader;
    private final VideoFileUploader videoFileUploader;

    @PostMapping
    public SuccessResponse<StoryEditResponse> createStory(@SessionMemberId Long memberId) {
        StoryEditResponse storyEditResponse = storyEditor.create(memberId);
        return SuccessResponse.of(SuccessCode.SUCCESS, storyEditResponse);
    }

    @PutMapping("/{storyId}/title")
    public SuccessResponse<Object> updateTitle(@PathVariable("storyId") Long storyId,
                                               @SessionMemberId Long memberId,
                                               @ModelAttribute @Valid StoryUpdateTitleRequest request) {
        storyEditor.updateTitle(storyId, memberId, request.title());
        return SuccessResponse.of(SuccessCode.SUCCESS);
    }

    @PutMapping("/{storyId}/comment-type")
    public SuccessResponse<Object> updateCommentType(@PathVariable("storyId") Long storyId,
                                                     @SessionMemberId Long memberId,
                                                     @ModelAttribute @Valid StoryUpdateCommentTypeRequest request) {
        storyEditor.updateCommentType(storyId, memberId, request.commentType());
        return SuccessResponse.of(SuccessCode.SUCCESS);
    }

    @DeleteMapping("/{storyId}/temporary")
    public SuccessResponse<Object> reset(@PathVariable("storyId") Long storyId,
                                         @SessionMemberId Long memberId) {
        storyEditor.reset(storyId, memberId);
        return SuccessResponse.of(SuccessCode.SUCCESS);
    }

    @PutMapping("/{storyId}/published")
    public SuccessResponse<Object> published(@PathVariable("storyId") Long storyId,
                                             @SessionMemberId Long memberId) {
        storyEditor.published(storyId, memberId);
        return SuccessResponse.of(SuccessCode.SUCCESS);
    }

    @DeleteMapping("/{storyId}")
    public SuccessResponse<Object> delete(@PathVariable("storyId") Long storyId,
                                          @SessionMemberId Long memberId) {
        storyEditor.delete(storyId, memberId);
        return SuccessResponse.of(SuccessCode.SUCCESS);
    }

    @PostMapping("/upload/image")
    public SuccessResponse<FileUploadResponse> uploadImage(@RequestPart("file") MultipartFile image,
                                                           @SessionMemberId Long memberId) {
        FileUploadResponse upload = imageFileUploader.upload(image, memberId);
        return SuccessResponse.of(SuccessCode.SUCCESS, upload);
    }

    @PostMapping("/upload/video")
    public SuccessResponse<FileUploadResponse> uploadVideo(@RequestPart("file") MultipartFile video,
                                                           @SessionMemberId Long memberId) {
        FileUploadResponse upload = videoFileUploader.upload(video, memberId);
        return SuccessResponse.of(SuccessCode.SUCCESS, upload);
    }

    @PutMapping("/{storyId}/pages/reorder")
    public SuccessResponse<Object> reorder(@PathVariable("storyId") Long storyId,
                                           @RequestBody StoryPageReorderRequest request,
                                           @SessionMemberId Long memberId) {
        storyPageEditor.reorder(storyId, memberId, request.pageOrders());
        return SuccessResponse.of(SuccessCode.SUCCESS);
    }
}
