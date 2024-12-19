package com.openclassrooms.MddApi.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.MddApi.dto.JwtResponseDto;
import com.openclassrooms.MddApi.dto.LoginDto;
import com.openclassrooms.MddApi.dto.RegisterDto;
import com.openclassrooms.MddApi.dto.UserResponseDto;
import com.openclassrooms.MddApi.entity.User;
import com.openclassrooms.MddApi.services.JwtService;
import com.openclassrooms.MddApi.services.UserService;
import org.springframework.security.core.Authentication;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;

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
    public JwtResponseDto register(@Valid @RequestBody RegisterDto registerDto) {

        String hashedPassword = passwordEncoder.encode(registerDto.getPassword());

        User user = new User();
        user.setUsername(registerDto.getUsername());
        user.setEmail(registerDto.getEmail());
        user.setPassword(hashedPassword);
        user.setCreated_at(new Date());
        user.setUpdated_at(new Date());
        userService.saveUser(user);

        JwtResponseDto jwtResponseDto = new JwtResponseDto(jwtService.generateJwtToken(user));
        return jwtResponseDto;

    }

    /**
     * @param loginDto loginDto
     * @param result   result
     * @return JwtResponseDto
     */
    @PostMapping("/login")
    public JwtResponseDto login(@Valid @RequestBody LoginDto loginDto) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));

        User user = userService.getUserByEmail(loginDto.getEmail());
        JwtResponseDto jwtResponseDto = new JwtResponseDto(jwtService.generateJwtToken(user));
        return jwtResponseDto;
    }

    /**
     * @param authentication auth
     * @return UserResponseDto
     */
    @GetMapping("/me")
    public UserResponseDto me(Authentication authentication) {

        var user = (User) authentication.getPrincipal();

        UserResponseDto userToReturn = new UserResponseDto(user);
        return userToReturn;
    }
}
