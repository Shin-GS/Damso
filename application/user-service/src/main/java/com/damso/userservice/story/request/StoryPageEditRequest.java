package com.damso.userservice.story.request;

import java.util.List;

public record StoryPageEditRequest(String text,
                                   String planText,
                                   List<String> files) {
}
