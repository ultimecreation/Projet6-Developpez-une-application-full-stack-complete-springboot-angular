package com.openclassrooms.MddApi.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.MddApi.entity.Comment;
import com.openclassrooms.MddApi.repository.CommentRepository;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    public List<Comment> getCommentsByPostId(int id) {
        return commentRepository.findAllCommentsByPostId(id);
    }

    public Comment createComment(Comment comment) {
        return commentRepository.save(comment);
    }
}
