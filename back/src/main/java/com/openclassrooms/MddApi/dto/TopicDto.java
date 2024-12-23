package com.openclassrooms.MddApi.dto;

import com.openclassrooms.MddApi.entity.Topic;

import lombok.Data;

@Data
public class TopicDto {

    private int id;
    private String name;
    private String description;

    public TopicDto(Topic topic) {
        id = topic.getId();
        name = topic.getName();
        description = topic.getDescription();
    }
}
