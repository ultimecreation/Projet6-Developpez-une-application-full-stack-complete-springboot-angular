package com.openclassrooms.MddApi.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.openclassrooms.MddApi.entity.User;

import lombok.Data;

@Data
public class UserResponseDto {

    private int id;
    private String username;
    private String email;
    @JsonFormat(pattern = "yyyy-dd-MM")
    private Date created_at;
    @JsonFormat(pattern = "yyyy-dd-MM")
    private Date updated_at;

    public UserResponseDto(User user) {
        id = user.getId();
        username = user.getUsername();
        email = user.getEmail();
        created_at = user.getCreated_at();
        updated_at = user.getUpdated_at();
    }
}
