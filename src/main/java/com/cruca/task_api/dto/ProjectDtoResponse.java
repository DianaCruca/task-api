package com.cruca.task_api.dto;

import com.cruca.task_api.enums.Status;
import lombok.Data;

@Data
public class ProjectDtoResponse {

    private Long projectId;
    private String name;
    private Status status;
    private UserContactDtoResponse createdBy;

}
