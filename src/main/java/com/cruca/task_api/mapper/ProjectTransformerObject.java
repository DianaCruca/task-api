package com.cruca.task_api.mapper;

import com.cruca.task_api.dto.ProjectDtoRequest;
import com.cruca.task_api.dto.ProjectDtoResponse;
import com.cruca.task_api.enums.Status;
import com.cruca.task_api.model.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class ProjectTransformerObject {

    @Autowired
    private ProjectMemberTransformerObject projectMemberTransformerObject;

    @Autowired
    private UserTransformerObject userTransformerObject;

    public Project dtoToEntity(ProjectDtoRequest projectDtoRequest) {
        Project project = new Project();
        project.setName(projectDtoRequest.getName());
        project.setStatus(Status.A);
        return project;
    }

    public ProjectDtoResponse entityToDto(Project project) {
        ProjectDtoResponse projectDtoResponse = new ProjectDtoResponse();
        projectDtoResponse.setProjectId(project.getProjectId());
        projectDtoResponse.setName(project.getName());
        projectDtoResponse.setStatus(project.getStatus());
        projectDtoResponse.setCreatedBy(userTransformerObject.entityToContactDto(project.getCreatedBy()));
        return projectDtoResponse;
    }

    public List<ProjectDtoResponse> listEntityToListDto(List<Project> projects) {
        if (projects == null || projects.isEmpty()) {
            return Collections.emptyList();
        }

        List<ProjectDtoResponse> response = new ArrayList<>();
        for (Project project : projects) {
            response.add(entityToDto(project));
        }
        return response;
    }

}
