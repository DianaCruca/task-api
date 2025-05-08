package com.cruca.task_api.service;

import com.cruca.task_api.dto.TaskMemberDtoRequest;
import com.cruca.task_api.dto.TaskMemberDtoResponse;
import com.cruca.task_api.enums.Status;

import java.util.List;

public interface TaskMemberService {

    TaskMemberDtoResponse create(TaskMemberDtoRequest taskMemberDtoRequest);

    TaskMemberDtoResponse readById(Long taskMemberId);

    List<TaskMemberDtoResponse> readByTaskAndStatus(Long projectTaskId, Status status);

    TaskMemberDtoResponse updateStatus(Long taskMemberId);

}
