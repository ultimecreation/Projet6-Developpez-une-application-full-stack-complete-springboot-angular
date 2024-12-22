package com.openclassrooms.MddApi.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.MddApi.dto.ApiResponse;
import com.openclassrooms.MddApi.dto.CommentDto;
import com.openclassrooms.MddApi.dto.CommentListDto;
import com.openclassrooms.MddApi.dto.CommentRequestDto;
import com.openclassrooms.MddApi.entity.Comment;
import com.openclassrooms.MddApi.entity.Post;
import com.openclassrooms.MddApi.entity.User;
import com.openclassrooms.MddApi.services.CommentService;
import com.openclassrooms.MddApi.services.PostService;
import com.openclassrooms.MddApi.services.UserService;

import jakarta.validation.Valid;
import javafx.scene.control.ListCell;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("api")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private UserService userService;

    @Autowired
    private PostService postService;

    @GetMapping("/posts/{id}/comments")
    public ResponseEntity<CommentListDto> getCommentsByPostId(@PathVariable int id) {

        List<Comment> commentList = commentService.getCommentsByPostId(id);
        List<CommentDto> commentDtoList = commentList.stream()
                .map((comment) -> new CommentDto(comment))
                .collect(Collectors.toList());
        CommentListDto commentListToReturn = new CommentListDto(commentDtoList);
        // Comment comment = Comment.builder()
        // .author(author)
        // .post(post)
        // .content(commentRequestDto.getContent())
        // .build();
        // commentService.createComment(comment);

        // ApiResponse apiResponse = ApiResponse.builder().message("Commentaire créé
        // !").build();
        return ResponseEntity.ok(commentListToReturn);
    }

    @PostMapping("/comments")
    public ResponseEntity<ApiResponse> createComment(@Valid @RequestBody CommentRequestDto commentRequestDto) {

        User author = userService.getUserById(Integer.parseInt(commentRequestDto.getAuthor_id()));
        Post post = postService.getPostById(Integer.parseInt(commentRequestDto.getPost_id()));

        Comment comment = Comment.builder()
                .author(author)
                .post(post)
                .content(commentRequestDto.getContent())
                .created_at(LocalDateTime.now())
                .build();
        commentService.createComment(comment);

        ApiResponse apiResponse = ApiResponse.builder().message("Commentaire créé !").build();
        return ResponseEntity.ok(apiResponse);
    }

}
