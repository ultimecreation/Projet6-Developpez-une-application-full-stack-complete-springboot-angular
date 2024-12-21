package com.openclassrooms.MddApi.controller;

import java.util.Date;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.MddApi.dto.ApiResponse;
import com.openclassrooms.MddApi.dto.LoginDto;
import com.openclassrooms.MddApi.dto.RegisterDto;
import com.openclassrooms.MddApi.dto.UpdateProfileRequestDto;
import com.openclassrooms.MddApi.dto.UserResponseDto;
import com.openclassrooms.MddApi.entity.User;
import com.openclassrooms.MddApi.services.JwtService;
import com.openclassrooms.MddApi.services.UserService;
import org.springframework.security.core.Authentication;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * @param registerDto registerDto
     * @param result      result
     * @return JwtResponseDto
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(@Valid @RequestBody RegisterDto registerDto) {

        String hashedPassword = passwordEncoder.encode(registerDto.getPassword());

        User user = new User();
        user.setUsername(registerDto.getUsername());
        user.setEmail(registerDto.getEmail());
        user.setPassword(hashedPassword);
        user.setCreated_at(new Date());
        user.setUpdated_at(new Date());
        userService.saveUser(user);

        ApiResponse apiResponse = ApiResponse.builder()
                .jwtToken(jwtService.generateJwtToken(user))
                .build();
        return ResponseEntity.ok(apiResponse);

    }

    /**
     * @param loginDto loginDto
     * @param result   result
     * @return JwtResponseDto
     */
    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@Valid @RequestBody LoginDto loginDto) {

        User user = userService.getByUsernameOrEmail(loginDto.getEmail(), loginDto.getEmail());
        if (user == null) {

            HashMap<String, String> errors = new HashMap<>();
            errors.put("invalidCredentials", "Les identifiants sont incorrects");
            ApiResponse apiResponse = ApiResponse.builder()
                    .errors(errors)
                    .build();
            return ResponseEntity.badRequest().body(apiResponse);

        }
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getEmail(), loginDto.getPassword()));

        ApiResponse apiResponse = ApiResponse.builder()
                .jwtToken(jwtService.generateJwtToken(user))
                .build();
        return ResponseEntity.ok(apiResponse);

    }

    /**
     * @param authentication auth
     * @return UserResponseDto
     */
    @GetMapping("/profile")
    public ResponseEntity<UserResponseDto> profile(Authentication authentication) {
        var user = (User) authentication.getPrincipal();

        UserResponseDto userToReturn = new UserResponseDto(user);
        return ResponseEntity.ok(userToReturn);
    }

    /**
     * @param authentication auth
     * @return UserResponseDto
     */
    @PutMapping("/profile")
    public ResponseEntity<ApiResponse> profileUpdate(
            @Valid @RequestBody UpdateProfileRequestDto updateProfileRequestDto,
            Authentication authentication) {

        User userToUpdate = userService.getUserById(updateProfileRequestDto.getId());
        userToUpdate.setUsername(updateProfileRequestDto.getUsername());
        userToUpdate.setEmail(updateProfileRequestDto.getEmail());
        if (updateProfileRequestDto.getPassword() != "") {
            String hashedPassword = passwordEncoder.encode(updateProfileRequestDto.getPassword());
            userToUpdate.setPassword(hashedPassword);
        }
        userService.saveUser(userToUpdate);

        // authenticationManager.authenticate( new
        // UsernamePasswordAuthenticationToken(userToUpdate.getEmail(),
        // userToUpdate.getPassword()));

        ApiResponse apiResponse = ApiResponse.builder()
                .jwtToken(jwtService.generateJwtToken(userToUpdate))
                .build();
        return ResponseEntity.ok(apiResponse);
        // UserResponseDto userToReturn = new UserResponseDto(userToUpdate);
        // return ResponseEntity.ok(userToReturn);
    }
}
