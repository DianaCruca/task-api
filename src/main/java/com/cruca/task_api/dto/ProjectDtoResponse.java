package com.cruca.task_api.dto;

import com.cruca.task_api.enums.Status;
import com.cruca.task_api.model.ProjectTask;
import lombok.Data;

import java.util.List;

@Data
public class ProjectDtoResponse {

    private Long projectId;
    private String name;
    private Status status;
    private UserContactDtoResponse createdBy;
    private List<ProjectTask> projectTasks;
    private List<ProjectMemberDtoResponse> projectMembers;

}
