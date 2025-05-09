package com.cruca.task_api.controller;

import com.cruca.task_api.dto.LoginDtoRequest;
import com.cruca.task_api.dto.LoginDtoResponse;
import com.cruca.task_api.dto.UserDtoRequest;
import com.cruca.task_api.dto.UserDtoResponse;
import com.cruca.task_api.exception.BadRequestException;
import com.cruca.task_api.payload.CorrectRequest;
import com.cruca.task_api.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;


@Tag(
        name = "Auth Controller",
        description = "This controller provides functionalities to create users and log-in"
)
@RestController
@RequestMapping("/api/public/user")
public class AuthController {

    @Autowired
    private UserService userService;

    @Operation(
            summary = "Register a new user by any user, without privileges",
            description = " Register a new user by any user, without assign privileges | Authentication: Not required",
            tags = { "Controller" },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Register user with name, lastname, email, password",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UserDtoRequest.class)
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Usuario creado correctamente",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = UserDtoResponse.class)
                            )
                    )
            }
    )
    @PreAuthorize("permitAll()")
    @PostMapping(value = "/register", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createUserPublic(@Valid @RequestBody UserDtoRequest userDtoRequest) {
        try {
            UserDtoResponse response = userService.createUserPublic(userDtoRequest);

            return new ResponseEntity<>(CorrectRequest.builder()
                    .status(1)
                    .message("Usuario registrado correctamente")
                    .data(response)
                    .time(new Date())
                    .build(),
                    HttpStatus.CREATED);
        } catch (DataAccessException exDt) {
            throw new BadRequestException(exDt.getMessage());
        }
    }

    @Operation(
            summary = "Login",
            description ="Login for Registered Users | Authentication: Not required",
            tags = { "Controller" },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Login with email and password",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = LoginDtoRequest.class)
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Usuario creado correctamente",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = LoginDtoResponse.class)
                            )
                    )
            }
    )
    @PreAuthorize("permitAll()")
    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> login(@RequestBody LoginDtoRequest dtoRequest) {
        try {
            LoginDtoResponse response = userService.login(dtoRequest);

            return new ResponseEntity<>(CorrectRequest.builder()
                    .status(1)
                    .message("Inicio de Sesión correcto")
                    .data(response)
                    .time(new Date())
                    .build(),
                    HttpStatus.OK);
        } catch (DataAccessException exDt) {
            throw new BadRequestException(exDt.getMessage());
        }
    }

}
