package com.openclassrooms.MddApi.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.MddApi.dto.ApiResponse;
import com.openclassrooms.MddApi.dto.PostDto;
import com.openclassrooms.MddApi.dto.PostListDto;
import com.openclassrooms.MddApi.dto.PostRequestDto;
import com.openclassrooms.MddApi.entity.Post;
import com.openclassrooms.MddApi.entity.Topic;
import com.openclassrooms.MddApi.entity.User;
import com.openclassrooms.MddApi.services.PostService;
import com.openclassrooms.MddApi.services.TopicService;
import com.openclassrooms.MddApi.services.UserService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("api")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    @Autowired
    private TopicService topicService;

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

    @PostMapping("/posts")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ApiResponse> createPost(@Valid @RequestBody PostRequestDto postRequestDto) {
        User author = userService.getUserById(Integer.parseInt(postRequestDto.getAuthor_id()));
        Topic topic = topicService.getTopicById(Integer.parseInt(postRequestDto.getTopic_id()));
        Post post = Post.builder()
                .author(author)
                .topic(topic)
                .title(postRequestDto.getTitle())
                .content(postRequestDto.getContent())
                .created_at(LocalDateTime.now())
                .build();
        postService.save(post);
        ApiResponse apiResponse = ApiResponse.builder().message("Article créé !").build();
        return ResponseEntity.ok(apiResponse);
    }

}
