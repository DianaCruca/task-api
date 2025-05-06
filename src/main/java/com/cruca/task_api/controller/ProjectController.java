package com.cruca.task_api.controller;

import com.cruca.task_api.dto.ProjectDtoRequest;
import com.cruca.task_api.dto.ProjectDtoResponse;
import com.cruca.task_api.enums.Status;
import com.cruca.task_api.exception.BadRequestException;
import com.cruca.task_api.payload.CorrectRequest;
import com.cruca.task_api.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/project")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping(value = "/register", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> create(@RequestBody ProjectDtoRequest projectDtoRequest) {
        try {
            ProjectDtoResponse response = projectService.createProject(projectDtoRequest);

            return new ResponseEntity<>(CorrectRequest.builder()
                    .status(1)
                    .message("Proyecto creado correctamente")
                    .data(response)
                    .time(new Date())
                    .build(),
                    HttpStatus.CREATED);
        } catch (DataAccessException exDt) {
            throw new BadRequestException(exDt.getMessage());
        }
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/read-id/{projectId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> readById(@PathVariable Long projectId) {
        try {
            ProjectDtoResponse response = projectService.readProject(projectId);

            return new ResponseEntity<>(CorrectRequest.builder()
                    .status(1)
                    .message("Proyecto listado correctamente")
                    .data(response)
                    .time(new Date())
                    .build(),
                    HttpStatus.OK);
        } catch (DataAccessException exDt) {
            throw new BadRequestException(exDt.getMessage());
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping(value = "/read-status/{status}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> readByStatus(@PathVariable Status status) {
        try {
            List<ProjectDtoResponse> response = projectService.readProjectByStatus(status);

            if (!response.isEmpty()) {
                return new ResponseEntity<>(CorrectRequest.builder()
                        .status(1)
                        .message("Proyectos listados correctamente")
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

    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/read-user/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> readByStatus(@PathVariable Long userId) {
        try {
            List<ProjectDtoResponse> response = projectService.readProjectByIdUser(userId);

            if (!response.isEmpty()) {
                return new ResponseEntity<>(CorrectRequest.builder()
                        .status(1)
                        .message("Proyectos listados correctamente")
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

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateUser(@RequestBody ProjectDtoRequest projectDtoRequest) {
        try {
            ProjectDtoResponse response = projectService.updateProject(projectDtoRequest);

            return new ResponseEntity<>(CorrectRequest.builder()
                    .status(1)
                    .message("Proyecto actualizado correctamente")
                    .data(response)
                    .time(new Date())
                    .build(),
                    HttpStatus.OK);
        } catch (DataAccessException exDt) {
            throw new BadRequestException(exDt.getMessage());
        }
    }

    @PreAuthorize("isAuthenticated()")
    @PutMapping(value = "/update-status/{idProject}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> inactivateProject(@PathVariable Long idProject) {
        try {
            ProjectDtoResponse response = projectService.updateStatus(idProject);

            return new ResponseEntity<>(CorrectRequest.builder()
                    .status(1)
                    .message("Estatus actualizado correctamente")
                    .data(response)
                    .time(new Date())
                    .build(),
                    HttpStatus.OK);
        } catch (DataAccessException exDt) {
            throw new BadRequestException(exDt.getMessage());
        }
    }

}
