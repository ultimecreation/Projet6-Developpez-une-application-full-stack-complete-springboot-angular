package com.openclassrooms.MddApi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.openclassrooms.MddApi.entity.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {

    @Query(value = "SELECT p.* FROM posts p WHERE topic_id IN (:ids)", nativeQuery = true)
    List<Post> findAllByTopicIds(List<Integer> ids);
}
