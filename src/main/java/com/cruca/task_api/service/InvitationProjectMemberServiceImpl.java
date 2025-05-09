package com.cruca.task_api.service;

import com.cruca.task_api.dto.InvitationProjectMemberDtoRequest;
import com.cruca.task_api.dto.InvitationProjectMemberDtoResponse;
import com.cruca.task_api.enums.InvitationStatus;
import com.cruca.task_api.enums.Status;
import com.cruca.task_api.exception.DuplicateResourceException;
import com.cruca.task_api.exception.ResourceNotFoundException;
import com.cruca.task_api.mapper.InvitationProjectMemberTransformerObject;
import com.cruca.task_api.model.InvitationProjectMember;
import com.cruca.task_api.model.Project;
import com.cruca.task_api.model.User;
import com.cruca.task_api.repository.InvitationProjectMemberRepository;
import com.cruca.task_api.repository.ProjectRepository;
import com.cruca.task_api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InvitationProjectMemberServiceImpl implements InvitationProjectMemberService {

    @Autowired
    private InvitationProjectMemberRepository invitationProjectMemberRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProjectMemberService projectMemberService;

    @Autowired
    private InvitationProjectMemberTransformerObject invitationProjectMemberTransformerObject;

    @Override
    public InvitationProjectMemberDtoResponse createInvitation(InvitationProjectMemberDtoRequest invitationProjectMemberDtoRequest) {
        Project project = projectRepository.findById(invitationProjectMemberDtoRequest.getProjectId())
                .orElseThrow(() -> new ResourceNotFoundException("Proyecto no encontrado, con id: " + invitationProjectMemberDtoRequest.getProjectId()));

        User user = userRepository.findByEmail(invitationProjectMemberDtoRequest.getEmailUser())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado, con email: " + invitationProjectMemberDtoRequest.getEmailUser()));

        if (invitationProjectMemberRepository.existsByUserAndProjectAndStatus(user, project, Status.A)) {
            throw new DuplicateResourceException("La invitación ya ha sido enviada");
        }

        InvitationProjectMember invitationProjectMember = invitationProjectMemberTransformerObject.dtoToEntity(invitationProjectMemberDtoRequest);
        invitationProjectMember.setUser(user);
        invitationProjectMember.setProject(project);

        return invitationProjectMemberTransformerObject.entityToDto(invitationProjectMemberRepository.save(invitationProjectMember));
    }

    @Override
    public List<InvitationProjectMemberDtoResponse> readInvitationsByUser(Long userId, Status status) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado, con id: " + userId));

        return invitationProjectMemberTransformerObject.listEntityToListDto(invitationProjectMemberRepository.findByUserAndStatus(user, status));
    }

    @Override
    public List<InvitationProjectMemberDtoResponse> readInvitationsByProject(Long projectId, Status status) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Proyecto no encontrado, con id: " + projectId));
        return invitationProjectMemberTransformerObject.listEntityToListDto(invitationProjectMemberRepository.findByProjectAndStatus(project, status));
    }

    @Override
    public InvitationProjectMemberDtoResponse updateInvitationStatus(Long projectMemberId, InvitationStatus invitationStatus) {
        InvitationProjectMember invitationProjectMember = invitationProjectMemberRepository.findById(projectMemberId)
                .orElseThrow(() -> new ResourceNotFoundException("Invitación no encontrada, con id: " + projectMemberId));

        invitationProjectMember.setInvitationStatus(invitationStatus);

        if (invitationStatus.equals(InvitationStatus.ACEPTADA)) {
            projectMemberService.createMember(invitationProjectMember);
        }

        return invitationProjectMemberTransformerObject.entityToDto(invitationProjectMemberRepository.save(invitationProjectMember));
    }

    @Override
    public InvitationProjectMemberDtoResponse updateStatus(Long invitationProjectMemberId) {
        InvitationProjectMember invitationProjectMember = invitationProjectMemberRepository.findById(invitationProjectMemberId)
                .orElseThrow(() -> new ResourceNotFoundException("Invitación no encontrada, con id: " + invitationProjectMemberId));

        Status status = null;
        if (invitationProjectMember.getStatus().equals(Status.A)) {
            status = Status.I;
        }

        if (invitationProjectMember.getStatus().equals(Status.I)) {
            status = Status.A;
        }

        invitationProjectMember.setStatus(status);

        return invitationProjectMemberTransformerObject.entityToDto(invitationProjectMemberRepository.save(invitationProjectMember));
    }

}
