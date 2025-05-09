package com.cruca.task_api.mapper;

import com.cruca.task_api.dto.InvitationProjectMemberDtoRequest;
import com.cruca.task_api.dto.InvitationProjectMemberDtoResponse;
import com.cruca.task_api.enums.InvitationStatus;
import com.cruca.task_api.enums.Status;
import com.cruca.task_api.model.InvitationProjectMember;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Component
public class InvitationProjectMemberTransformerObject {

    @Autowired
    private UserTransformerObject userTransformerObject;

    public InvitationProjectMember dtoToEntity(InvitationProjectMemberDtoRequest invitationProjectMemberDtoRequest) {
        InvitationProjectMember projectMember = new InvitationProjectMember();

        projectMember.setInvitationStatus(InvitationStatus.PENDIENTE);
        projectMember.setStatus(Status.A);
        projectMember.setProjectRole(invitationProjectMemberDtoRequest.getProjectRole());
        projectMember.setDateSent(new Date());

        return projectMember;
    }

    public InvitationProjectMemberDtoResponse entityToDto(InvitationProjectMember invitationProjectMember) {
        InvitationProjectMemberDtoResponse response = new InvitationProjectMemberDtoResponse();

        response.setInvitationProjectMemberId(invitationProjectMember.getInvitationProjectMemberId());
        response.setInvitationStatus(invitationProjectMember.getInvitationStatus());
        response.setDateSent(invitationProjectMember.getDateSent());
        response.setProjectRole(invitationProjectMember.getProjectRole());
        response.setProjectId(invitationProjectMember.getProject().getProjectId());
        response.setStatus(invitationProjectMember.getStatus());
        response.setUser(userTransformerObject.entityToContactDto(invitationProjectMember.getUser()));
        return response;
    }

    public List<InvitationProjectMemberDtoResponse> listEntityToListDto(List<InvitationProjectMember> invitationProjectMembers) {
        if (invitationProjectMembers == null || invitationProjectMembers.isEmpty()) {
            return Collections.emptyList();
        }

        List<InvitationProjectMemberDtoResponse> response = new ArrayList<>();
        for (InvitationProjectMember projectMember : invitationProjectMembers) {
            response.add(entityToDto(projectMember));
        }
        return response;
    }

}
