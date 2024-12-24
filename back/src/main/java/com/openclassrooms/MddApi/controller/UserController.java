package com.openclassrooms.MddApi.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.MddApi.dto.ApiResponse;
import com.openclassrooms.MddApi.dto.PostDto;
import com.openclassrooms.MddApi.dto.PostListDto;
import com.openclassrooms.MddApi.dto.TopicDto;
import com.openclassrooms.MddApi.dto.TopicListDto;
import com.openclassrooms.MddApi.dto.UpdateProfileRequestDto;
import com.openclassrooms.MddApi.dto.UserResponseDto;
import com.openclassrooms.MddApi.entity.Post;
import com.openclassrooms.MddApi.entity.Topic;
import com.openclassrooms.MddApi.entity.User;
import com.openclassrooms.MddApi.services.JwtService;
import com.openclassrooms.MddApi.services.PostService;
import com.openclassrooms.MddApi.services.TopicService;
import com.openclassrooms.MddApi.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/user")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private TopicService topicService;

    @Autowired
    private PostService postService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * @param authentication
     * @return ResponseEntity<UserResponseDto>
     */
    @GetMapping("/profile")
    public ResponseEntity<UserResponseDto> profile(Authentication authentication) {
        var user = (User) authentication.getPrincipal();

        UserResponseDto userToReturn = new UserResponseDto(user);
        return ResponseEntity.ok(userToReturn);
    }

    /**
     * @param updateProfileRequestDto
     * @param authentication
     * @return ResponseEntity<ApiResponse>
     */
    @PutMapping("/profile")
    public ResponseEntity<ApiResponse> profileUpdate(
            @Valid @RequestBody UpdateProfileRequestDto updateProfileRequestDto,
            Authentication authentication) {

        User userToUpdate = userService.getUserById(Integer.parseInt(updateProfileRequestDto.getId()));
        userToUpdate.setUsername(updateProfileRequestDto.getUsername());
        userToUpdate.setEmail(updateProfileRequestDto.getEmail());
        if (updateProfileRequestDto.getPassword() != "") {
            String hashedPassword = passwordEncoder.encode(updateProfileRequestDto.getPassword());
            userToUpdate.setPassword(hashedPassword);
        }
        userService.saveUser(userToUpdate);

        ApiResponse apiResponse = ApiResponse.builder()
                .jwtToken(jwtService.generateJwtToken(userToUpdate))
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    /**
     * @param authentication
     * @return ResponseEntity<TopicListDto>
     */
    @GetMapping("/topics")
    public ResponseEntity<TopicListDto> getUserTopics(Authentication authentication) {

        int userId = ((User) authentication.getPrincipal()).getId();
        User userFound = userService.getUserById(userId);
        List<TopicDto> topicDtoList = userFound.getTopics().stream()
                .map((topic) -> new TopicDto(topic))
                .collect(Collectors.toList());
        TopicListDto topicListToReturn = new TopicListDto(topicDtoList);

        return ResponseEntity.ok(topicListToReturn);
    }

    /**
     * @param authentication
     * @return ResponseEntity<ApiResponse>
     */
    @GetMapping("/topics-to-subscribe")
    public ResponseEntity<?> getUserTopicsToSubscribe(Authentication authentication) {

        int userId = ((User) authentication.getPrincipal()).getId();
        List<Topic> topics = topicService.getAllTopicsToSubscribeToByUserId(userId);
        List<TopicDto> topicDtoList = topics.stream()
                .map((topic) -> new TopicDto(topic))
                .collect(Collectors.toList());
        TopicListDto topicListToReturn = new TopicListDto(topicDtoList);

        return ResponseEntity.ok(topicListToReturn);
    }

    /**
     * @param id
     * @param authentication
     * @return ResponseEntity<ApiResponse>
     */
    @GetMapping("/topics/add/{id}")
    public ResponseEntity<ApiResponse> addUserTopic(@PathVariable int id, Authentication authentication) {

        int userId = ((User) authentication.getPrincipal()).getId();
        User userToSave = userService.getUserById(userId);
        Topic topic = topicService.getTopicById(id);

        userToSave.getTopics().add(topic);
        userService.saveUser(userToSave);

        ApiResponse apiResponse = ApiResponse.builder().message("Thème ajouté !").build();
        return ResponseEntity.ok(apiResponse);
    }

    /**
     * @param id
     * @param authentication
     * @return ResponseEntity<ApiResponse>
     */
    @DeleteMapping("/topics/remove/{id}")

    public ResponseEntity<ApiResponse> removeUserTopic(@PathVariable int id, Authentication authentication) {

        int userId = ((User) authentication.getPrincipal()).getId();
        User userToSave = userService.getUserById(userId);
        Topic topic = topicService.getTopicById(id);

        userToSave.getTopics().remove(topic);
        userService.saveUser(userToSave);

        ApiResponse apiResponse = ApiResponse.builder().message("Thème supprimé de la liste !").build();
        return ResponseEntity.ok(apiResponse);
    }

    /**
     * @param authentication
     * @return ResponseEntity<PostListDto<
     */
    @GetMapping("/feed")
    public ResponseEntity<PostListDto> getUserFeed(Authentication authentication) {

        int userId = ((User) authentication.getPrincipal()).getId();
        List<Post> posts = postService.getUserFeed(userId);
        List<PostDto> postDtoList = posts.stream()
                .map((post) -> new PostDto(post))
                .collect(Collectors.toList());
        PostListDto postListToReturn = new PostListDto(postDtoList);

        return ResponseEntity.ok(postListToReturn);
    }

}
