package com.cruca.task_api.dto;

import com.cruca.task_api.enums.Status;
import com.cruca.task_api.enums.TaskPriority;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ProjectTaskDtoResponse {

    private ProjectDtoResponse project;
    private Long taskId;
    private String title;
    private String description;
    private Date createdAt;
    private Date dueDate;
    private TaskPriority taskPriority;
    private String taskStatus;
    private Status status;
    private UserContactDtoResponse createdBy;
    private List<TaskMemberDtoResponse> taskMember;

}
