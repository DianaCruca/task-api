package com.cruca.task_api.service;

import com.cruca.task_api.dto.ProjectTaskDtoRequest;
import com.cruca.task_api.dto.ProjectTaskDtoResponse;
import com.cruca.task_api.enums.Status;
import com.cruca.task_api.enums.TaskPriority;
import com.cruca.task_api.exception.ResourceNotFoundException;
import com.cruca.task_api.mapper.ProjectTaskTransformerObject;
import com.cruca.task_api.model.Project;
import com.cruca.task_api.model.ProjectTask;
import com.cruca.task_api.model.User;
import com.cruca.task_api.repository.ProjectRepository;
import com.cruca.task_api.repository.ProjectTaskRepository;
import com.cruca.task_api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectTaskServiceImpl implements ProjectTaskService {

    @Autowired
    private ProjectTaskRepository projectTaskRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthorizationService authorizationService;

    @Autowired
    private ProjectTaskTransformerObject projectTaskTransformerObject;

    @Override
    public ProjectTaskDtoResponse create(ProjectTaskDtoRequest projectTaskDtoRequest) {
        Project project = projectRepository.findById(projectTaskDtoRequest.getProjectId())
                .orElseThrow(() -> new ResourceNotFoundException("Proyecto no encontrado, con id: " + projectTaskDtoRequest.getProjectId()));

        String email = authorizationService.getEmail();

        /// Identify the creator
        User userCreator = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado, con email: " + email));

        ProjectTask projectTask = projectTaskTransformerObject.dtoToEntity(projectTaskDtoRequest);
        projectTask.setCreatedBy(userCreator);
        projectTask.setProject(project);

        return projectTaskTransformerObject.entityToDto(projectTaskRepository.save(projectTask));
    }

    @Override
    public ProjectTaskDtoResponse readById(Long taskId) {
        ProjectTask projectTask = projectTaskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Tarea no encontrada, con id: " + taskId));

        return projectTaskTransformerObject.entityToDto(projectTask);
    }

    @Override
    public List<ProjectTaskDtoResponse> readByProject(Long projectId, Status status) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Proyecto no encontrado, con id: " + projectId));

        return projectTaskTransformerObject.listEntityToDto(projectTaskRepository.findByProjectAndStatus(project, status));
    }

    @Override
    public List<ProjectTaskDtoResponse> readByUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado, con id: " + userId));

        return projectTaskTransformerObject.listEntityToDto(projectTaskRepository.findByTaskMemberUser(user));
    }

    @Override
    public List<ProjectTaskDtoResponse> readByProjectAndTaskPriority(Long projectId, TaskPriority taskPriority) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Proyecto no encontrado, con id: " + projectId));

        return projectTaskTransformerObject.listEntityToDto(projectTaskRepository.findByProjectAndTaskPriority(project, taskPriority));
    }

    @Override
    public List<ProjectTaskDtoResponse> readByProjectAndUserAndTaskPriority(Long projectId, Long userId, TaskPriority taskPriority) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado, con id: " + userId));

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Proyecto no encontrado, con id: " + projectId));

        return projectTaskTransformerObject.listEntityToDto(projectTaskRepository.findByProjectAndTaskPriorityAndTaskMemberUser(project, taskPriority, user));
    }

    @Override
    public ProjectTaskDtoResponse update(ProjectTaskDtoRequest projectTaskDtoRequest) {
        ProjectTask projectTask = projectTaskRepository.findById(projectTaskDtoRequest.getTaskId())
                .orElseThrow(() -> new ResourceNotFoundException("Tarea no encontrada, con id: " + projectTaskDtoRequest.getTaskId()));

        ///Here are the roles that have authorization to modify sensitive data
        List<String> authorizedRoles = List.of("ROLE_ADMIN");
        ///Verify that the user has authorization to update sensitive data
        if (authorizationService.identifyIsAuthorized(authorizedRoles)) {
            ///Allow updating of sensitive data
            projectTask = updateWithAuthorization(projectTask, projectTaskDtoRequest);
        }

        projectTask.setTaskStatus(projectTaskDtoRequest.getTaskStatus());

        return projectTaskTransformerObject.entityToDto(projectTaskRepository.save(projectTask));
    }

    public ProjectTask updateWithAuthorization(ProjectTask projectTask, ProjectTaskDtoRequest projectTaskDtoRequest) {
        projectTask.setTitle(projectTaskDtoRequest.getTitle());
        projectTask.setDescription(projectTaskDtoRequest.getDescription());
        projectTask.setDueDate(projectTaskDtoRequest.getDueDate());
        projectTask.setTaskPriority(projectTaskDtoRequest.getTaskPriority());

        return projectTask;
    }

    @Override
    public ProjectTaskDtoResponse updateStatus(Long taskId) {
        ProjectTask projectTask = projectTaskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Tarea no encontrada, con id: " + taskId));

        Status status = null;
        if (projectTask.getStatus().equals(Status.A)) {
            status = Status.I;
        }

        if (projectTask.getStatus().equals(Status.I)) {
            status = Status.A;
        }

        projectTask.setStatus(status);
        return projectTaskTransformerObject.entityToDto(projectTaskRepository.save(projectTask));
    }

}
