package com.openclassrooms.MddApi.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class CommentRequestDto {

    @NotEmpty(message = "L'identifiant de l'article est requis")
    private String post_id;

    @NotEmpty(message = "Le contenu est requis")
    private String content;

}
