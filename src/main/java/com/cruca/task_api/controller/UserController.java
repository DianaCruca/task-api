package com.cruca.task_api.controller;

import com.cruca.task_api.dto.UserDtoRequest;
import com.cruca.task_api.dto.UserDtoResponse;
import com.cruca.task_api.enums.Status;
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
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Tag(
        name = "User Controller",
        description = "This controller provides functionalities to create, update user data and read by ID and status"
)
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Operation(
            summary = "User register from internal users with privileges",
            description = "Register a new user by an admin, with the ability to assign privileges | Authentication: Required - Role: Admin",
            tags = { "Controller" },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Register user with name, lastname, email, password and role",
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
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping(value = "/register", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createUserPrivate(@Valid @RequestBody UserDtoRequest userDtoRequest) {
        try {
            UserDtoResponse response = userService.createUserPrivate(userDtoRequest);

            return new ResponseEntity<>(CorrectRequest.builder()
                    .status(1)
                    .message("Usuario creado correctamente")
                    .data(response)
                    .time(new Date())
                    .build(),
                    HttpStatus.CREATED);
        } catch (DataAccessException exDt) {
            throw new BadRequestException(exDt.getMessage());
        }
    }

    @Operation(
            summary = "Get user by ID",
            description = "Get user by ID | Authentication: Required - Role: Any",
            tags = { "Controller" },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Usuario listado correctamente",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = UserDtoResponse.class)
                            )
                    )
            }
    )
    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/read-id/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> readUsersById(@PathVariable Long id) {
        try {
            UserDtoResponse response = userService.readUserById(id);

            return new ResponseEntity<>(CorrectRequest.builder()
                    .status(1)
                    .message("Usuario listado correctamente")
                    .data(response)
                    .time(new Date())
                    .build(),
                    HttpStatus.OK);
        } catch (DataAccessException exDt) {
            throw new BadRequestException(exDt.getMessage());
        }
    }

    @Operation(
            summary = "Get user by Status",
            description = "Get user by Status | Authentication: Required - Role: Admin",
            tags = { "Controller" },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Usuario listado correctamente",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = UserDtoResponse.class)
                            )
                    )
            }
    )
    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping(value = "/read-status/{status}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> readUsersByStatus(@PathVariable Status status) {
        try {
            List<UserDtoResponse> response = userService.readUsersByStatus(status);

            if (!response.isEmpty()) {
                return new ResponseEntity<>(CorrectRequest.builder()
                        .status(1)
                        .message("Usuarios listados correctamente")
                        .data(response)
                        .time(new Date())
                        .build(),
                        HttpStatus.OK);
            } else {
                return new ResponseEntity<>(CorrectRequest.builder()
                        .status(1)
                        .message("No hay registros en el sistema")
                        .data(response)
                        .time(new Date())
                        .build(),
                        HttpStatus.NO_CONTENT);
            }
        } catch (DataAccessException exDt) {
            throw new BadRequestException(exDt.getMessage());
        }
    }

    @Operation(
            summary = "Update user data",
            description = "Update user data | Authentication: Required - Role: any",
            tags = { "Controller" },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    description = """
                    key: role - this field can only be updated by an admin <br>
                    key: status - this field can only be updated by an admin <br>
                    """,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UserDtoRequest.class)
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Usuario actualizado correctamente",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = UserDtoResponse.class)
                            )
                    )
            }
    )
    @PreAuthorize("isAuthenticated()")
    @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateUser(@RequestBody UserDtoRequest userDtoRequest) {
        try {
            UserDtoResponse response = userService.updateUser(userDtoRequest);

            return new ResponseEntity<>(CorrectRequest.builder()
                    .status(1)
                    .message("Usuario actualizado correctamente")
                    .data(response)
                    .time(new Date())
                    .build(),
                    HttpStatus.OK);
        } catch (DataAccessException exDt) {
            throw new BadRequestException(exDt.getMessage());
        }
    }

}
