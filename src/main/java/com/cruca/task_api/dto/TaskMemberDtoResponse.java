package com.cruca.task_api.dto;

import com.cruca.task_api.enums.Status;
import lombok.Data;

import java.util.Date;

@Data
public class TaskMemberDtoResponse {

    private Long taskMemberId;
    private Date dateAssigned;
    private UserContactDtoResponse user;
    private Status status;

}
