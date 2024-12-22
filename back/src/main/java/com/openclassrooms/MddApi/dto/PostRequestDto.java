package com.openclassrooms.MddApi.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class PostRequestDto {

    @NotEmpty(message = "L'identifiant du theme est requis")
    private String topic_id;

    @NotEmpty(message = "Le titre est requis")
    private String title;

    @NotEmpty(message = "Le contenu est requis")
    private String content;

}
