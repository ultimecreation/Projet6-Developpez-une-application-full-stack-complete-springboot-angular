package com.openclassrooms.MddApi.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.openclassrooms.MddApi.entity.Post;

import lombok.Data;

@Data
public class PostDto {

    private int id;
    private int topic_id;
    private int author_id;
    private String title;
    private String content;

    @JsonFormat(pattern = "yyyy-dd-MM")
    private LocalDateTime created_at;

    public PostDto(Post post) {
        id = post.getId();
        topic_id = post.getTopic().getId();
        author_id = post.getAuthor().getId();
        title = post.getTitle();
        content = post.getContent();
        created_at = post.getCreated_at();
    }

}
