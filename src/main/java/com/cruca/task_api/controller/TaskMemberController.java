package com.cruca.task_api.controller;

import com.cruca.task_api.dto.TaskMemberDtoRequest;
import com.cruca.task_api.dto.TaskMemberDtoResponse;
import com.cruca.task_api.enums.Status;
import com.cruca.task_api.exception.BadRequestException;
import com.cruca.task_api.payload.CorrectRequest;
import com.cruca.task_api.service.TaskMemberService;
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
@RequestMapping("/api/task/member")
public class TaskMemberController {

    @Autowired
    private TaskMemberService taskMemberService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping(value = "/register", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> create(@RequestBody TaskMemberDtoRequest taskMemberDtoRequest) {
        try {
            TaskMemberDtoResponse response = taskMemberService.create(taskMemberDtoRequest);

            return new ResponseEntity<>(CorrectRequest.builder()
                    .status(1)
                    .message("Miembro agregado correctamente")
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
            TaskMemberDtoResponse response = taskMemberService.readById(taskId);

            return new ResponseEntity<>(CorrectRequest.builder()
                    .status(1)
                    .message("Miembros listados correctamente")
                    .data(response)
                    .time(new Date())
                    .build(),
                    HttpStatus.OK);
        } catch (DataAccessException exDt) {
            throw new BadRequestException(exDt.getMessage());
        }
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/read-task-status/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> readByProjectAndStatus(@RequestParam Long taskMemberId,
                                                    @RequestParam Status status) {
        try {
            List<TaskMemberDtoResponse> response = taskMemberService.readByTaskAndStatus(taskMemberId, status);

            if (!response.isEmpty()) {
                return new ResponseEntity<>(CorrectRequest.builder()
                        .status(1)
                        .message("Miembros listados correctamente")
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
    @PutMapping(value = "/update-status/{taskMemberId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> inactivateProject(@PathVariable Long taskMemberId) {
        try {
            TaskMemberDtoResponse response = taskMemberService.updateStatus(taskMemberId);

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
