package com.cruca.task_api.dto;

import com.cruca.task_api.enums.Status;
import com.cruca.task_api.enums.TaskPriority;
import com.cruca.task_api.enums.TaskStatus;
import lombok.Data;

import java.util.Date;

@Data
public class ProjectTaskDtoRequest {

    private Long taskId;
    private String title;
    private String description;
    private Date dueDate;
    private TaskPriority taskPriority;
    private TaskStatus taskStatus;
    private Long projectId;
    private Status status;

}
