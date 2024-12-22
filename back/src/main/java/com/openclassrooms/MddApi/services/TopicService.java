package com.openclassrooms.MddApi.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.MddApi.entity.Topic;
import com.openclassrooms.MddApi.repository.TopicRepository;

@Service
public class TopicService {

    @Autowired
    private TopicRepository topicRepository;

    public List<Topic> getAllTopics() {
        return topicRepository.findAll();
    }

    public List<Topic> getAllTopicsToSubscribeToByUserId(Integer userId) {
        return topicRepository.findAllTopicsNotSubscribeYetByUserId(userId);
    }

    public Topic getTopicById(int id) {
        return topicRepository.findById(id).orElseThrow();
    }

}
