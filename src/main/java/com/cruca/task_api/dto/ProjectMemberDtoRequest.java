package com.cruca.task_api.dto;

import com.cruca.task_api.enums.InvitationStatus;
import com.cruca.task_api.enums.ProjectRole;
import lombok.Data;

@Data
public class ProjectMemberDtoRequest {

    private Long projectMemberId;
    private InvitationStatus invitationStatus;
    private ProjectRole projectRole;
    private String emailUser;

}
