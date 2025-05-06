package com.cruca.task_api.mapper;

import com.cruca.task_api.dto.ProjectMemberDtoRequest;
import com.cruca.task_api.dto.ProjectMemberDtoResponse;
import com.cruca.task_api.enums.InvitationStatus;
import com.cruca.task_api.model.ProjectMember;

import java.util.ArrayList;
import java.util.List;

public class ProjectMemberTransformerObject {

    private UserTransformerObject userTransformerObject;

    public ProjectMember dtoToEntity(ProjectMemberDtoRequest projectMemberDtoRequest) {
        ProjectMember projectMember = new ProjectMember();

        projectMember.setInvitationStatus(InvitationStatus.PENDIENTE);
        projectMember.setProjectRole(projectMemberDtoRequest.getProjectRole());

        return projectMember;
    }

    public ProjectMemberDtoResponse entityToDto(ProjectMember projectMember) {
        userTransformerObject = new UserTransformerObject();
        ProjectMemberDtoResponse response = new ProjectMemberDtoResponse();

        response.setProjectMemberId(projectMember.getProjectMemberId());
        response.setInvitationStatus(projectMember.getInvitationStatus());
        response.setJoinedAt(projectMember.getJoinedAt());
        response.setUser(userTransformerObject.entityToContactDto(projectMember.getUser()));
        return response;
    }

    public List<ProjectMemberDtoResponse> listDtoToListEntity(List<ProjectMember> projectMembers) {
        if (projectMembers.isEmpty()) {
            return null;
        }

        List<ProjectMemberDtoResponse> response = new ArrayList<>();
        for (ProjectMember projectMember : projectMembers) {
            response.add(entityToDto(projectMember));
        }
        return response;
    }

}
