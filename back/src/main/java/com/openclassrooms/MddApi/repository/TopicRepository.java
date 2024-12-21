package com.openclassrooms.MddApi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.openclassrooms.MddApi.entity.Topic;

@Repository
public interface TopicRepository extends JpaRepository<Topic, Integer> {

}
