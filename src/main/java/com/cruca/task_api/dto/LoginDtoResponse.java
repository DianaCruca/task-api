package com.cruca.task_api.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginDtoResponse {

    private UserDtoResponse usuario;
    private String token;

}
