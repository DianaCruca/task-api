package com.cruca.task_api.dto;

import com.cruca.task_api.enums.ProjectRole;
import com.cruca.task_api.enums.Status;
import lombok.Data;

import java.util.Date;

@Data
public class ProjectMemberDtoResponse {

    private Long projectMemberId;
    private ProjectRole projectRole;
    private Date joinedAt;
    private UserContactDtoResponse user;
    private Status status;

}
