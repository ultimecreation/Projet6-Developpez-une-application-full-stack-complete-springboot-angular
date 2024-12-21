package com.openclassrooms.MddApi.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.MddApi.dto.TopicDto;
import com.openclassrooms.MddApi.dto.TopicListDto;
import com.openclassrooms.MddApi.entity.Topic;
import com.openclassrooms.MddApi.services.TopicService;
import org.springframework.web.bind.annotation.GetMapping;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("api")
public class TopicController {

    @Autowired
    private TopicService topicService;

    @GetMapping("/topics")
    public ResponseEntity<TopicListDto> getAllTopics() {
        List<Topic> topics = topicService.getAllTopics();
        List<TopicDto> topicDtoList = topics.stream()
                .map((Topic topic) -> new TopicDto(topic))
                .collect(Collectors.toList());

        TopicListDto topicListDto = new TopicListDto(topicDtoList);
        return ResponseEntity.ok(topicListDto);
    }

}
