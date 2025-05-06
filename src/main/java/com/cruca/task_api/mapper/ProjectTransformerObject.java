package com.cruca.task_api.mapper;

import com.cruca.task_api.dto.ProjectDtoRequest;
import com.cruca.task_api.dto.ProjectDtoResponse;
import com.cruca.task_api.enums.Status;
import com.cruca.task_api.model.Project;

import java.util.*;

public class ProjectTransformerObject {

    private ProjectMemberTransformerObject projectMemberTransformerObject;
    private UserTransformerObject userTransformerObject;

    public Project dtoToEntity(ProjectDtoRequest projectDtoRequest) {
        Project project = new Project();
        project.setName(projectDtoRequest.getName());
        project.setStatus(Status.A);

        return project;
    }

    public ProjectDtoResponse entityToDto(Project project) {
        userTransformerObject = new UserTransformerObject();
        projectMemberTransformerObject = new ProjectMemberTransformerObject();

        ProjectDtoResponse projectDtoResponse = new ProjectDtoResponse();
        projectDtoResponse.setProjectId(project.getProjectId());
        projectDtoResponse.setName(project.getName());
        projectDtoResponse.setStatus(project.getStatus());
        projectDtoResponse.setProjectMembers(projectMemberTransformerObject.listDtoToListEntity(project.getProjectMember()));
        projectDtoResponse.setCreatedBy(userTransformerObject.entityToContactDto(project.getCreatedBy()));
        return projectDtoResponse;
    }

    public List<ProjectDtoResponse> listEntityToListDto(List<Project> projects) {
        if (projects.isEmpty()) {
            return null;
        }

        List<ProjectDtoResponse> response = new ArrayList<>();
        for (Project project : projects) {
            response.add(entityToDto(project));
        }
        return response;
    }

}
