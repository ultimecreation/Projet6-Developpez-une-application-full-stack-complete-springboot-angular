package com.openclassrooms.MddApi.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class UpdateProfileRequestDto {

    private int id;

    @NotEmpty(message = "Le nom d'utilisateur est requis")
    private String username;

    @NotEmpty(message = "L'E-mail est requis")
    @Email(message = "Le format de l'e-mail n'est pas valide")
    private String email;

    private String password;

}
