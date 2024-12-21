package com.openclassrooms.MddApi.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.MddApi.dto.PostDto;
import com.openclassrooms.MddApi.dto.PostListDto;
import com.openclassrooms.MddApi.entity.Post;
import com.openclassrooms.MddApi.services.PostService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("api")
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping("/posts")
    public ResponseEntity<PostListDto> getAllPosts() {
        List<Post> posts = postService.getAllPosts();
        List<PostDto> postDtoList = posts.stream()
                .map((post) -> new PostDto(post))
                .collect(Collectors.toList());
        PostListDto postListToReturn = new PostListDto(postDtoList);
        return ResponseEntity.ok(postListToReturn);
    }

    @GetMapping("/posts/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable int id) {
        Post post = postService.getPostById(id);
        PostDto postToReturn = new PostDto(post);
        return ResponseEntity.ok(postToReturn);
    }

}
