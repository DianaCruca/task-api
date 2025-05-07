package com.cruca.task_api.mapper;

import com.cruca.task_api.dto.TaskMemberDtoResponse;
import com.cruca.task_api.model.TaskMember;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class TaskMemberTransformerObject {

    @Autowired
    private UserTransformerObject userTransformerObject;

    public TaskMemberDtoResponse entityToDto(TaskMember taskMember) {
        TaskMemberDtoResponse response = new TaskMemberDtoResponse();
        response.setTaskMemberId(taskMember.getTaskMemberId());
        response.setDateAssigned(taskMember.getDateAssigned());
        response.setUser(userTransformerObject.entityToContactDto(taskMember.getUser()));
        return response;
    }

    public List<TaskMemberDtoResponse> listEntityToListDto(List<TaskMember> taskMembers) {
        if (taskMembers == null || taskMembers.isEmpty()) {
            return Collections.emptyList();
        }

        List<TaskMemberDtoResponse> response = new ArrayList<>();
        for (TaskMember taskMember : taskMembers) {
            response.add(entityToDto(taskMember));
        }
        return response;
    }

}
