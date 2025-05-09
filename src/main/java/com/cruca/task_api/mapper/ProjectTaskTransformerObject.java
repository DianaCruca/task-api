package com.cruca.task_api.mapper;

import com.cruca.task_api.dto.ProjectTaskDtoRequest;
import com.cruca.task_api.dto.ProjectTaskDtoResponse;
import com.cruca.task_api.enums.Status;
import com.cruca.task_api.model.ProjectTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class ProjectTaskTransformerObject {

    @Autowired
    private UserTransformerObject userTransformerObject;

    @Autowired
    private ProjectTransformerObject projectTransformerObject;

    public ProjectTask dtoToEntity(ProjectTaskDtoRequest projectTaskDtoRequest) {
        ProjectTask projectTask = new ProjectTask();
        projectTask.setTitle(projectTaskDtoRequest.getTitle());
        projectTask.setDescription(projectTaskDtoRequest.getDescription());
        projectTask.setDueDate(projectTaskDtoRequest.getDueDate());
        projectTask.setTaskPriority(projectTaskDtoRequest.getTaskPriority());
        projectTask.setTaskStatus(projectTaskDtoRequest.getTaskStatus());
        projectTask.setStatus(Status.A);
        return projectTask;
    }

    public ProjectTaskDtoResponse entityToDto(ProjectTask projectTask) {
        ProjectTaskDtoResponse response = new ProjectTaskDtoResponse();
        response.setProject(projectTransformerObject.entityToDto(projectTask.getProject()));
        response.setTaskId(projectTask.getTaskId());
        response.setTitle(projectTask.getTitle());
        response.setDescription(projectTask.getDescription());
        response.setCreatedAt(projectTask.getCreatedAt());
        response.setDueDate(projectTask.getDueDate());
        response.setTaskPriority(projectTask.getTaskPriority());
        response.setTaskStatus(projectTask.getTaskStatus().getStatusCode());
        response.setStatus(projectTask.getStatus());
        response.setCreatedBy(userTransformerObject.entityToContactDto(projectTask.getCreatedBy()));
        return response;
    }

    public List<ProjectTaskDtoResponse> listEntityToDto(List<ProjectTask> projectTasks) {
        if (projectTasks == null || projectTasks.isEmpty()) {
            return Collections.emptyList();
        }

        List<ProjectTaskDtoResponse> response = new ArrayList<>();
        for (ProjectTask projectTask : projectTasks) {
            response.add(entityToDto(projectTask));
        }
        return response;
    }

}
