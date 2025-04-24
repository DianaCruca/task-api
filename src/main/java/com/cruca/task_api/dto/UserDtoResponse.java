package com.cruca.task_api.dto;

import com.cruca.task_api.enums.Role;
import com.cruca.task_api.enums.Status;
import lombok.Data;

@Data
public class UserDtoResponse {

    private Long id;
    private String name;
    private String lastname;
    private String email;
    private Role role;
    private Status status;

}
