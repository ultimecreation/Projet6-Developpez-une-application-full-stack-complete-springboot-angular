package com.openclassrooms.MddApi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.openclassrooms.MddApi.entity.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {

}
