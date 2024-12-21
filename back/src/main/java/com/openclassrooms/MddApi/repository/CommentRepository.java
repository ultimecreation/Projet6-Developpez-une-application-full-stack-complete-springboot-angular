package com.openclassrooms.MddApi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.openclassrooms.MddApi.entity.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {

}
