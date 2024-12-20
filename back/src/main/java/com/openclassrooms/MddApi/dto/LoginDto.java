package com.openclassrooms.MddApi.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class LoginDto {

    @NotEmpty(message = "L'E-mail ou nom d'utilisateur est requis")
    private String email;

    @NotEmpty(message = "Le mot de passe est requis")
    private String password;
}
