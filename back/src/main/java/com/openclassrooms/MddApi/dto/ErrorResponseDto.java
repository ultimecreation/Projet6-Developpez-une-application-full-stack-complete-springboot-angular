package com.openclassrooms.MddApi.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ErrorResponseDto {

    private String message;

    public ErrorResponseDto(String message) {

        this.message = message;

    }
}
