package com.cruca.task_api.controller;

import com.cruca.task_api.dto.InvitationProjectMemberDtoRequest;
import com.cruca.task_api.dto.InvitationProjectMemberDtoResponse;
import com.cruca.task_api.enums.InvitationStatus;
import com.cruca.task_api.enums.Status;
import com.cruca.task_api.exception.BadRequestException;
import com.cruca.task_api.payload.CorrectRequest;
import com.cruca.task_api.service.InvitationProjectMemberService;
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
@RequestMapping("/api/project/member-invitation")
public class InvitationProjectMemberController {

    @Autowired
    private InvitationProjectMemberService invitationProjectMemberService;

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping(value = "/register", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> create(@RequestBody InvitationProjectMemberDtoRequest invitationProjectMemberDtoRequest) {
        try {
            InvitationProjectMemberDtoResponse response = invitationProjectMemberService.createInvitation(invitationProjectMemberDtoRequest);

            return new ResponseEntity<>(CorrectRequest.builder()
                    .status(1)
                    .message("Invitación creada correctamente")
                    .data(response)
                    .time(new Date())
                    .build(),
                    HttpStatus.CREATED);
        } catch (DataAccessException exDt) {
            throw new BadRequestException(exDt.getMessage());
        }
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/read-user", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> readByUser(@RequestParam Long userId,
                                        @RequestParam Status status) {
        try {
            List<InvitationProjectMemberDtoResponse> response = invitationProjectMemberService.readInvitationsByUser(userId, status);

            if (!response.isEmpty()) {
                return new ResponseEntity<>(CorrectRequest.builder()
                        .status(1)
                        .message("Invitaciones listadas correctamente")
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
    @GetMapping(value = "/read-project", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> readByProject(@RequestParam Long projectId,
                                           @RequestParam Status status) {
        try {
            List<InvitationProjectMemberDtoResponse> response = invitationProjectMemberService.readInvitationsByProject(projectId, status);

            if (!response.isEmpty()) {
                return new ResponseEntity<>(CorrectRequest.builder()
                        .status(1)
                        .message("Invitaciones listadas correctamente")
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
    @PutMapping(value = "/update-status-invitation", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateStatusInvitation(@RequestParam Long projectMemberId,
                                    @RequestParam InvitationStatus invitationStatus) {
        try {
            InvitationProjectMemberDtoResponse response = invitationProjectMemberService.updateInvitationStatus(projectMemberId, invitationStatus);

            return new ResponseEntity<>(CorrectRequest.builder()
                    .status(1)
                    .message("Invitación actualizada correctamente")
                    .data(response)
                    .time(new Date())
                    .build(),
                    HttpStatus.OK);
        } catch (DataAccessException exDt) {
            throw new BadRequestException(exDt.getMessage());
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PutMapping(value = "/update-status/{invitationProjectMemberId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateStatus(@PathVariable Long invitationProjectMemberId) {
        try {
            InvitationProjectMemberDtoResponse response = invitationProjectMemberService.updateStatus(invitationProjectMemberId);

            return new ResponseEntity<>(CorrectRequest.builder()
                    .status(1)
                    .message("Invitación actualizada correctamente")
                    .data(response)
                    .time(new Date())
                    .build(),
                    HttpStatus.OK);
        } catch (DataAccessException exDt) {
            throw new BadRequestException(exDt.getMessage());
        }
    }

}
