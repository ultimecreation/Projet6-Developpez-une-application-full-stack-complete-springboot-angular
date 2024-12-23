package com.openclassrooms.MddApi.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.openclassrooms.MddApi.entity.Post;

import lombok.Data;

@Data
public class PostDto {

    private int id;
    private int topic_id;
    private String topic_name;
    private int author_id;
    private String author_username;
    private String title;
    private String content;

    @JsonFormat(pattern = "yyyy-dd-MM")
    private LocalDateTime created_at;

    public PostDto(Post post) {
        id = post.getId();
        topic_id = post.getTopic().getId();
        topic_name = post.getTopic().getName();
        author_id = post.getAuthor().getId();
        author_username = post.getAuthor().getUsername();
        title = post.getTitle();
        content = post.getContent();
        created_at = post.getCreated_at();
    }

}
