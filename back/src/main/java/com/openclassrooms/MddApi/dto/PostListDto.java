package com.openclassrooms.MddApi.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PostListDto {

    List<PostDto> posts;
}
