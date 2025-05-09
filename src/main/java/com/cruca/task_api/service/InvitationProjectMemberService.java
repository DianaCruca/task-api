package com.cruca.task_api.service;

import com.cruca.task_api.dto.InvitationProjectMemberDtoRequest;
import com.cruca.task_api.dto.InvitationProjectMemberDtoResponse;
import com.cruca.task_api.enums.InvitationStatus;
import com.cruca.task_api.enums.Status;

import java.util.List;

public interface InvitationProjectMemberService {

    InvitationProjectMemberDtoResponse createInvitation(InvitationProjectMemberDtoRequest invitationProjectMemberDtoRequest);

    List<InvitationProjectMemberDtoResponse> readInvitationsByUser(Long userId, Status status);

    List<InvitationProjectMemberDtoResponse> readInvitationsByProject(Long projectId, Status status);

    InvitationProjectMemberDtoResponse updateInvitationStatus(Long projectMemberId, InvitationStatus invitationStatus);

    InvitationProjectMemberDtoResponse updateStatus(Long invitationProjectMemberId);

}
