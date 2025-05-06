package com.cruca.task_api.mapper;

import com.cruca.task_api.dto.ProjectMemberDtoRequest;
import com.cruca.task_api.dto.ProjectMemberDtoResponse;
import com.cruca.task_api.enums.InvitationStatus;
import com.cruca.task_api.model.ProjectMember;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class ProjectMemberTransformerObject {

    @Autowired
    private UserTransformerObject userTransformerObject;

    public ProjectMember dtoToEntity(ProjectMemberDtoRequest projectMemberDtoRequest) {
        ProjectMember projectMember = new ProjectMember();

        projectMember.setInvitationStatus(InvitationStatus.PENDIENTE);
        projectMember.setProjectRole(projectMemberDtoRequest.getProjectRole());

        return projectMember;
    }

    public ProjectMemberDtoResponse entityToDto(ProjectMember projectMember) {
        ProjectMemberDtoResponse response = new ProjectMemberDtoResponse();

        response.setProjectMemberId(projectMember.getProjectMemberId());
        response.setInvitationStatus(projectMember.getInvitationStatus());
        response.setJoinedAt(projectMember.getJoinedAt());
        response.setProjectRole(projectMember.getProjectRole());
        response.setUser(userTransformerObject.entityToContactDto(projectMember.getUser()));
        return response;
    }

    public List<ProjectMemberDtoResponse> listDtoToListEntity(List<ProjectMember> projectMembers) {
        if (projectMembers == null || projectMembers.isEmpty()) {
            return Collections.emptyList();
        }

        List<ProjectMemberDtoResponse> response = new ArrayList<>();
        for (ProjectMember projectMember : projectMembers) {
            response.add(entityToDto(projectMember));
        }
        return response;
    }

}
