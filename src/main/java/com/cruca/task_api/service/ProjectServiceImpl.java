package com.cruca.task_api.service;

import com.cruca.task_api.dto.ProjectDtoRequest;
import com.cruca.task_api.dto.ProjectDtoResponse;
import com.cruca.task_api.enums.ProjectRole;
import com.cruca.task_api.enums.Status;
import com.cruca.task_api.exception.AccessDeniedException;
import com.cruca.task_api.exception.ResourceNotFoundException;
import com.cruca.task_api.mapper.ProjectMemberTransformerObject;
import com.cruca.task_api.mapper.ProjectTransformerObject;
import com.cruca.task_api.model.Project;
import com.cruca.task_api.model.ProjectMember;
import com.cruca.task_api.model.User;
import com.cruca.task_api.repository.ProjectMemberReository;
import com.cruca.task_api.repository.ProjectRepository;
import com.cruca.task_api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ProjectMemberReository projectMemberReository;

    @Autowired
    private AuthorizationService authorizationService;

    @Autowired
    private ProjectTransformerObject projectTransformerObject;

    @Autowired
    private ProjectMemberTransformerObject projectMemberTransformerObject;

    @Override
    public ProjectDtoResponse createProject(ProjectDtoRequest projectDtoRequest) {
        ;
        /// Identify the user's email that logged in
        String email = authorizationService.getEmail();
        /// Identify the authenticated user
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado, con email: " + email));

        /// Convert dto to entity
        Project project = projectTransformerObject.dtoToEntity(projectDtoRequest);
        /// Assign the creator user
        project.setCreatedBy(user);
        /// Save the project
        Project projectSave = projectRepository.save(project);

        /// Assign like member to creator user
        Set<ProjectMember> projectMembers = new HashSet<>();
        ProjectMember projectMember = new ProjectMember();
        projectMember.setProjectRole(ProjectRole.ADMINISTRATOR);
        projectMember.setJoinedAt(new Date());
        projectMember.setUser(user);
        projectMember.setProject(projectSave);
        projectMembers.add(projectMember);
        ProjectMember projectMemberSave = projectMemberReository.save(projectMember);

        return projectTransformerObject.entityToDto(projectMemberSave.getProject());
    }

    @Override
    public ProjectDtoResponse readProject(Long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Proyecto no encontrado, con id: " + projectId));

        return projectTransformerObject.entityToDto(project);
    }

    @Override
    public List<ProjectDtoResponse> readProjectByIdUser(Long idUser) {
        String email = authorizationService.getEmail();

        User user = userRepository.findById(idUser)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado, con id: " + idUser));

        /// Check if the user has read permissions
        /// The user is allowed to read the projects if at least one of the following conditions is met:
        /// 1. The user is the owner of the data
        boolean isOwner = email.equals(user.getEmail());

        ///Here are the roles that have authorization to read data from other users
        List<String> authorizedRoles = List.of("ROLE_ADMIN");
        /// 2. The user has an authorized role
        boolean isAuthorized = authorizationService.identifyIsAuthorized(authorizedRoles);

        if (!isAuthorized && !isOwner) {
            throw new AccessDeniedException("Acceso Demegado, no tienes permisos");
        }

        return projectTransformerObject.listEntityToListDto(projectRepository.findByStatusAndProjectMemberUser(Status.A, user));
    }

    @Override
    public List<ProjectDtoResponse> readProjectByStatus(Status status) {
        return projectTransformerObject.listEntityToListDto(projectRepository.findByStatus(status));
    }

    /// Private functionality
    /// Role:
    @Override
    public ProjectDtoResponse updateProject(ProjectDtoRequest projectDtoRequest) {
        Project project = projectRepository.findById(projectDtoRequest.getProjectId())
                .orElseThrow(() -> new ResourceNotFoundException("Proyecto no encontrado, con id: " + projectDtoRequest.getProjectId()));

        project.setName(projectDtoRequest.getName());

        return projectTransformerObject.entityToDto(projectRepository.save(project));
    }

    @Override
    public ProjectDtoResponse updateStatus(Long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Proyecto no encontrado, con id: " + projectId));

        Status newStatus = null;

        if (project.getStatus().equals(Status.A)) {
            newStatus = Status.I;
        }

        if (project.getStatus().equals(Status.I)) {
            newStatus = Status.A;
        }

        project.setStatus(newStatus);

        return projectTransformerObject.entityToDto(projectRepository.save(project));
    }

}
