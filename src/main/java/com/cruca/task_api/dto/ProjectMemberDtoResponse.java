package com.cruca.task_api.dto;

import com.cruca.task_api.enums.InvitationStatus;
import com.cruca.task_api.enums.ProjectRole;
import lombok.Data;

import java.util.Date;

@Data
public class ProjectMemberDtoResponse {

    private Long projectMemberId;
    private InvitationStatus invitationStatus;
    private ProjectRole projectRole;
    private Date joinedAt;
    private UserContactDtoResponse user;

}
