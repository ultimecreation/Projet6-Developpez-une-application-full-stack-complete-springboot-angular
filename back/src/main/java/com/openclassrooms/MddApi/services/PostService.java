package com.openclassrooms.MddApi.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.MddApi.entity.Post;
import com.openclassrooms.MddApi.repository.PostRepository;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public List<Post> getUserFeed(List<Integer> ids) {
        return postRepository.findAllByTopicIds(ids);
    }

    public Post getPostById(int id) {
        return postRepository.findById(id).orElseThrow();
    }

    public Post createPost(Post post) {
        return postRepository.save(post);
    }

    public Post save(Post post) {
        return postRepository.save(post);
    }
}
