package com.cruca.task_api.controller;

import com.cruca.task_api.dto.ProjectTaskDtoRequest;
import com.cruca.task_api.dto.ProjectTaskDtoResponse;
import com.cruca.task_api.enums.Status;
import com.cruca.task_api.enums.TaskPriority;
import com.cruca.task_api.exception.BadRequestException;
import com.cruca.task_api.payload.CorrectRequest;
import com.cruca.task_api.service.ProjectTaskService;
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
@RequestMapping("/api/project/task")
public class ProjectTaskController {

    @Autowired
    private ProjectTaskService projectTaskService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping(value = "/register", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> create(@RequestBody ProjectTaskDtoRequest projectTaskDtoRequest) {
        try {
            ProjectTaskDtoResponse response = projectTaskService.create(projectTaskDtoRequest);

            return new ResponseEntity<>(CorrectRequest.builder()
                    .status(1)
                    .message("Tarea creada correctamente")
                    .data(response)
                    .time(new Date())
                    .build(),
                    HttpStatus.CREATED);
        } catch (DataAccessException exDt) {
            throw new BadRequestException(exDt.getMessage());
        }
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/read-id/{taskId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> readById(@PathVariable Long taskId) {
        try {
            ProjectTaskDtoResponse response = projectTaskService.readById(taskId);

            return new ResponseEntity<>(CorrectRequest.builder()
                    .status(1)
                    .message("Tarea listada correctamente")
                    .data(response)
                    .time(new Date())
                    .build(),
                    HttpStatus.OK);
        } catch (DataAccessException exDt) {
            throw new BadRequestException(exDt.getMessage());
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping(value = "/read-project-status/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> readByProjectAndStatus(@RequestParam Long projectId,
                                                    @RequestParam Status status) {
        try {
            List<ProjectTaskDtoResponse> response = projectTaskService.readByProject(projectId, status);

            if (!response.isEmpty()) {
                return new ResponseEntity<>(CorrectRequest.builder()
                        .status(1)
                        .message("Tareas listadas correctamente")
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
    public ResponseEntity<?> readByUserAndStatus(@PathVariable Long userId) {
        try {
            List<ProjectTaskDtoResponse> response = projectTaskService.readByUser(userId);

            if (!response.isEmpty()) {
                return new ResponseEntity<>(CorrectRequest.builder()
                        .status(1)
                        .message("Tareas listadas correctamente")
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
    @GetMapping(value = "/read-project-priority/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> readByProjectAndTaskPriorityAndStatus(@RequestParam Long projectId,
                                                                   @RequestParam TaskPriority taskPriority) {
        try {
            List<ProjectTaskDtoResponse> response = projectTaskService.readByProjectAndTaskPriority(projectId, taskPriority);

            if (!response.isEmpty()) {
                return new ResponseEntity<>(CorrectRequest.builder()
                        .status(1)
                        .message("Tareas listadas correctamente")
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
    @GetMapping(value = "/read-project-user-priority/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> readByProjectAndUserAndTaskPriority(@RequestParam Long projectId,
                                                                 @RequestParam Long userId,
                                                                 @RequestParam TaskPriority taskPriority) {
        try {
            List<ProjectTaskDtoResponse> response = projectTaskService.readByProjectAndUserAndTaskPriority(projectId, userId, taskPriority);

            if (!response.isEmpty()) {
                return new ResponseEntity<>(CorrectRequest.builder()
                        .status(1)
                        .message("Tareas listadas correctamente")
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
    @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateUser(@RequestBody ProjectTaskDtoRequest projectTaskDtoRequest) {
        try {
            ProjectTaskDtoResponse response = projectTaskService.update(projectTaskDtoRequest);

            return new ResponseEntity<>(CorrectRequest.builder()
                    .status(1)
                    .message("Tarea actualizada correctamente")
                    .data(response)
                    .time(new Date())
                    .build(),
                    HttpStatus.OK);
        } catch (DataAccessException exDt) {
            throw new BadRequestException(exDt.getMessage());
        }
    }

    @PreAuthorize("isAuthenticated()")
    @PutMapping(value = "/update-status/{taskId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> inactivateProject(@PathVariable Long taskId) {
        try {
            ProjectTaskDtoResponse response = projectTaskService.updateStatus(taskId);

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
