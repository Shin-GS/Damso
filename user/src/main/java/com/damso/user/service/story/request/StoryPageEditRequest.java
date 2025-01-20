package com.damso.user.service.story.request;

import java.util.List;

public record StoryPageEditRequest(String text,
                                   String planText,
                                   List<String> files) {
}
