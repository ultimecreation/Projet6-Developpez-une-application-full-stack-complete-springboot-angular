package com.openclassrooms.MddApi.dto;

import com.openclassrooms.MddApi.validation.UniqueEmailConstraint;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class RegisterDto {

    @NotEmpty(message = "Le nom d'utilisateur est requis")
    private String username;

    @NotEmpty(message = "L'E-mail est requis")
    @Email(message = "Le format de l'e-mail n'est pas valide")
    @UniqueEmailConstraint
    private String email;

    @NotEmpty(message = "Le mot de passe est requis")
    private String password;

}
