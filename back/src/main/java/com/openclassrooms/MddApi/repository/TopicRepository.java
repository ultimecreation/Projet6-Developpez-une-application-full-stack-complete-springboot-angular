package com.openclassrooms.MddApi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.openclassrooms.MddApi.entity.Topic;

@Repository
public interface TopicRepository extends JpaRepository<Topic, Integer> {

    String sqlString = """
            SELECT t.* FROM topics t
            WHERE id
            NOT IN (
                SELECT ut.topic_id FROM user_topics ut
                WHERE ut.user_id=:userId
            )
            """;

    @Query(value = sqlString, nativeQuery = true)
    List<Topic> findAllTopicsNotSubscribeYetByUserId(Integer userId);
}
