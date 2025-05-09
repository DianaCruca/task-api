package com.cruca.task_api.dto;

import com.cruca.task_api.enums.Role;
import com.cruca.task_api.enums.Status;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserDtoRequest {

    private Long id;

    @NotBlank(message = " El nombre es obligatorio")
    private String name;

    @NotBlank(message = " El apellido es obligatorio")
    private String lastname;

    @NotBlank(message = " El email es obligatorio")
    private String email;

    @NotBlank(message = " La contraseña es obligatoria")
    private String password;

    private Role role;

    private Status status;

}
