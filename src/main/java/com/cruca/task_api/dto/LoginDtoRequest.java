package com.cruca.task_api.dto;

import lombok.Data;

@Data
public class LoginDtoRequest {

    private String email;
    private String password;

}
