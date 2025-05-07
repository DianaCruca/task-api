package com.cruca.task_api.dto;

import lombok.Data;

@Data
public class TaskMemberDtoRequest {

    private Long taskMemberId;
    private Long idProjectTask;
    private Long idUser;

}
