package com.openclassrooms.MddApi.dto;

import com.openclassrooms.MddApi.entity.Topic;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
