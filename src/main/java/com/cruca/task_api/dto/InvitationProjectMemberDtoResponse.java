package com.cruca.task_api.dto;

import com.cruca.task_api.enums.InvitationStatus;
import com.cruca.task_api.enums.ProjectRole;
import com.cruca.task_api.enums.Status;
import lombok.Data;

import java.util.Date;

@Data
public class InvitationProjectMemberDtoResponse {

    private Long invitationProjectMemberId;
    private Long projectId;
    private InvitationStatus invitationStatus;
    private ProjectRole projectRole;
    private Date dateSent;
    private UserContactDtoResponse user;
    private Status status;

}
