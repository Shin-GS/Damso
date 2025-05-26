package com.damso.userservice.story.request;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class StorySearchRequest {
    private String keyword;
    private List<String> category = new ArrayList<>();
    private String sort = "created_at";
    private Integer page = 1;
    private Integer size = 15;
}
