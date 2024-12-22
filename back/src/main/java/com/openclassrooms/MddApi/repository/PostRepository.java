package com.openclassrooms.MddApi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.openclassrooms.MddApi.entity.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {

    String sqlString = """
            SELECT p.* FROM posts p
            WHERE topic_id
            IN (
                SELECT ut.topic_id FROM user_topics ut
                WHERE ut.user_id=:userId
            )
            """;

    @Query(value = sqlString, nativeQuery = true)
    List<Post> findAllPostsByTopicsAndUserId(Integer userId);
}
