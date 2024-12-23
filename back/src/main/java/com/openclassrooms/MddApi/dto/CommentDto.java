package com.openclassrooms.MddApi.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.openclassrooms.MddApi.entity.Comment;

import lombok.Data;

@Data
public class CommentDto {

    private int id;

    private int author_id;
    private String content;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime created_at;

    public CommentDto(Comment comment) {
        id = comment.getId();
        author_id = comment.getAuthor().getId();
        content = comment.getContent();
        created_at = comment.getCreated_at();
    }
}
