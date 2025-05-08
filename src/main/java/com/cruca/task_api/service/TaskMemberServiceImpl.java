package com.cruca.task_api.service;

import com.cruca.task_api.dto.TaskMemberDtoRequest;
import com.cruca.task_api.dto.TaskMemberDtoResponse;
import com.cruca.task_api.enums.Status;
import com.cruca.task_api.exception.DuplicateResourceException;
import com.cruca.task_api.exception.ResourceNotFoundException;
import com.cruca.task_api.mapper.TaskMemberTransformerObject;
import com.cruca.task_api.model.*;
import com.cruca.task_api.repository.ProjectMemberRepository;
import com.cruca.task_api.repository.ProjectTaskRepository;
import com.cruca.task_api.repository.TaskMemberRepository;
import com.cruca.task_api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskMemberServiceImpl implements TaskMemberService {

    @Autowired
    private TaskMemberRepository taskMemberRepository;

    @Autowired
    private TaskMemberTransformerObject taskMemberTransformerObject;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProjectTaskRepository projectTaskRepository;

    @Autowired
    private ProjectMemberRepository projectMemberRepository;

    @Override
    public TaskMemberDtoResponse create(TaskMemberDtoRequest taskMemberDtoRequest){
        User user = userRepository.findById(taskMemberDtoRequest.getUserAssignedId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado, con id: " + taskMemberDtoRequest.getUserAssignedId()));

        ProjectTask projectTask = projectTaskRepository.findById(taskMemberDtoRequest.getProjectTaskId())
                .orElseThrow(() -> new ResourceNotFoundException("Tarea no encontrada, con id: " + taskMemberDtoRequest.getProjectTaskId()));

        if (!projectMemberRepository.existsByUserAndProject(user, projectTask.getProject())) {
            throw new ResourceNotFoundException("No es posible asignarle la tarea al usuario: " + taskMemberDtoRequest.getUserAssignedId() + " porque no es miembro del proyecto");
        }

        if (taskMemberRepository.existsByUserAndProjectTask(user, projectTask)){
            throw new DuplicateResourceException("El usuario ya esta asignado a la tarea con id " + taskMemberDtoRequest.getProjectTaskId() );
        }

        TaskMember taskMember = taskMemberTransformerObject.dtoToEntity(taskMemberDtoRequest);
        taskMember.setUser(user);
        taskMember.setProjectTask(projectTask);

        return taskMemberTransformerObject.entityToDto(taskMemberRepository.save(taskMember));
    }

    @Override
    public TaskMemberDtoResponse readById(Long taskMemberId){
        TaskMember taskMember = taskMemberRepository.findById(taskMemberId)
                .orElseThrow(() -> new ResourceNotFoundException("Miembro no encontrado, con id: " + taskMemberId));
        return taskMemberTransformerObject.entityToDto(taskMember);
    }

    @Override
    public List<TaskMemberDtoResponse> readByTaskAndStatus(Long projectTaskId, Status status){
        ProjectTask projectTask = projectTaskRepository.findById(projectTaskId)
                .orElseThrow(() -> new ResourceNotFoundException("Tarea no encontrada, con id: " + projectTaskId));

        return taskMemberTransformerObject.listEntityToListDto(taskMemberRepository.findByProjectTaskAndStatus(projectTask, status));
    }

    @Override
    public TaskMemberDtoResponse updateStatus(Long taskMemberId){
        TaskMember taskMember = taskMemberRepository.findById(taskMemberId)
                .orElseThrow(() -> new ResourceNotFoundException("Miembro no encontrado, con id: " + taskMemberId));

        Status status = null;
        if (taskMember.getStatus().equals(Status.A)) {
            status = Status.I;
        }

        if (taskMember.getStatus().equals(Status.I)) {
            status = Status.A;
        }

        taskMember.setStatus(status);
        return taskMemberTransformerObject.entityToDto(taskMemberRepository.save(taskMember));
    }


}
