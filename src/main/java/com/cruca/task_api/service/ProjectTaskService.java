package com.cruca.task_api.service;

import com.cruca.task_api.dto.ProjectTaskDtoRequest;
import com.cruca.task_api.dto.ProjectTaskDtoResponse;
import com.cruca.task_api.enums.Status;
import com.cruca.task_api.enums.TaskPriority;

import java.util.List;

public interface ProjectTaskService {

    ProjectTaskDtoResponse create(ProjectTaskDtoRequest projectTaskDtoRequest);

    ProjectTaskDtoResponse readById(Long taskId);

    List<ProjectTaskDtoResponse> readByProject(Long projectId, Status status);

    List<ProjectTaskDtoResponse> readByUser(Long userId);

    List<ProjectTaskDtoResponse> readByProjectAndTaskPriority(Long projectId, TaskPriority taskPriority);

    List<ProjectTaskDtoResponse> readByProjectAndUserAndTaskPriority(Long projectId, Long userId, TaskPriority taskPriority);

    ProjectTaskDtoResponse update(ProjectTaskDtoRequest projectTaskDtoRequest);

    ProjectTaskDtoResponse updateStatus(Long taskId);

}
