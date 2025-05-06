package com.cruca.task_api.service;

import com.cruca.task_api.dto.ProjectMemberDtoResponse;
import com.cruca.task_api.model.InvitationProjectMember;

public interface ProjectMemberService {

    ProjectMemberDtoResponse createMember(InvitationProjectMember invitationProjectMember);

    ProjectMemberDtoResponse updateStatus(Long projectMemberId);

}
