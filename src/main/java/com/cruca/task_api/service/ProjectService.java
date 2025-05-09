package com.cruca.task_api.service;

import com.cruca.task_api.dto.ProjectDtoRequest;
import com.cruca.task_api.dto.ProjectDtoResponse;
import com.cruca.task_api.enums.Status;

import java.util.List;

public interface ProjectService {

    ProjectDtoResponse createProject(ProjectDtoRequest projectDtoRequest);

    ProjectDtoResponse readProject(Long idProject);

    List<ProjectDtoResponse> readProjectByIdUser(Long idUser);

    List<ProjectDtoResponse> readProjectByStatus(Status status);

    ProjectDtoResponse updateProject(ProjectDtoRequest projectDtoRequest);

    ProjectDtoResponse updateStatus(Long projectId);

}
