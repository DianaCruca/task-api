package com.cruca.task_api.dto;

import com.cruca.task_api.enums.Status;
import com.cruca.task_api.model.ProjectMember;
import com.cruca.task_api.model.ProjectTask;
import lombok.Data;

import java.util.Set;

@Data
public class ProjectDtoRequest {

    private Long projectId;
    private String name;
    private Status status;
    private Set<ProjectTask> projectTasks;
    private Set<ProjectMember> projectMembers;

}
