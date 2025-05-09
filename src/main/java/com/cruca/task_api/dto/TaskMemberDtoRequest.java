package com.cruca.task_api.dto;

import com.cruca.task_api.enums.Status;
import lombok.Data;

import java.util.Date;

@Data
public class TaskMemberDtoRequest {

    private Long taskMemberId;
    private Date dateAssigned;
    private Long projectTaskId;
    private Long userAssignedId;
    private Status status;

}
