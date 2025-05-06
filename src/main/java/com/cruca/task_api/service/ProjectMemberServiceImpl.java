package com.cruca.task_api.service;

import com.cruca.task_api.dto.ProjectMemberDtoResponse;
import com.cruca.task_api.enums.Status;
import com.cruca.task_api.exception.ResourceNotFoundException;
import com.cruca.task_api.mapper.ProjectMemberTransformerObject;
import com.cruca.task_api.model.InvitationProjectMember;
import com.cruca.task_api.model.ProjectMember;
import com.cruca.task_api.repository.ProjectMemberReository;
import com.cruca.task_api.repository.ProjectRepository;
import com.cruca.task_api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ProjectMemberServiceImpl implements ProjectMemberService {

    @Autowired
    private ProjectMemberReository projectMemberReository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProjectMemberTransformerObject projectMemberTransformerObject;

    @Override
    public ProjectMemberDtoResponse createMember(InvitationProjectMember invitationProjectMember) {
        ProjectMember projectMember = new ProjectMember();
        projectMember.setProjectRole(invitationProjectMember.getProjectRole());
        projectMember.setJoinedAt(new Date());
        projectMember.setUser(invitationProjectMember.getUser());
        projectMember.setProject(invitationProjectMember.getProject());
        projectMember.setStatus(Status.A);

        return projectMemberTransformerObject.entityToDto(projectMemberReository.save(projectMember));
    }

    @Override
    public ProjectMemberDtoResponse updateStatus(Long projectMemberId) {
        ProjectMember projectMember = projectMemberReository.findById(projectMemberId)
                .orElseThrow(() -> new ResourceNotFoundException("Invitación no encontrada, con id: " + projectMemberId));

        Status status = null;
        if (projectMember.getStatus().equals(Status.A)) {
            status = Status.I;
        }

        if (projectMember.getStatus().equals(Status.I)) {
            status = Status.A;
        }

        projectMember.setStatus(status);

        return projectMemberTransformerObject.entityToDto(projectMemberReository.save(projectMember));
    }

}
