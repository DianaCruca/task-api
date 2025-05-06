package com.cruca.task_api.controller;

import com.cruca.task_api.dto.ProjectMemberDtoResponse;
import com.cruca.task_api.exception.BadRequestException;
import com.cruca.task_api.payload.CorrectRequest;
import com.cruca.task_api.service.ProjectMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/api/project/member")
public class ProjectMemberController {

    @Autowired
    private ProjectMemberService projectMemberService;

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PutMapping(value = "/update-status/{projectMemberId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> inactivateProject(@PathVariable Long projectMemberId) {
        try {
            ProjectMemberDtoResponse response = projectMemberService.updateStatus(projectMemberId);

            return new ResponseEntity<>(CorrectRequest.builder()
                    .status(1)
                    .message("Miembro actualizado correctamente")
                    .data(response)
                    .time(new Date())
                    .build(),
                    HttpStatus.OK);
        } catch (DataAccessException exDt) {
            throw new BadRequestException(exDt.getMessage());
        }
    }

}
