package com.openclassrooms.MddApi.dto;

import java.util.HashMap;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApiResponse {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String data;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private HashMap<String, String> errors;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String jwtToken;

}
