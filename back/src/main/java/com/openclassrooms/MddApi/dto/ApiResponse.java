package com.openclassrooms.MddApi.dto;

import java.util.HashMap;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApiResponse {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private HashMap<String, String> errors;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String jwtToken;

}
