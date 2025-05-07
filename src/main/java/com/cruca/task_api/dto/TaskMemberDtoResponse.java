package com.cruca.task_api.dto;

import lombok.Data;

import java.util.Date;

@Data
public class TaskMemberDtoResponse {

    private Long taskMemberId;
    private Date dateAssigned;
    private UserContactDtoResponse user;

}
