package com.openclassrooms.MddApi.dto;

import com.openclassrooms.MddApi.validation.UniqueEmailConstraint;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class RegisterDto {

    @NotEmpty(message = "name is required")
    private String username;

    @NotEmpty(message = "email is required")
    @Email(message = "invalid email format")
    @UniqueEmailConstraint
    private String email;

    @NotEmpty(message = "password is required")
    private String password;

}
