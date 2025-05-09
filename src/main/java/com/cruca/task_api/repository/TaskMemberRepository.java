package com.cruca.task_api.repository;

import com.cruca.task_api.enums.Status;
import com.cruca.task_api.model.ProjectTask;
import com.cruca.task_api.model.TaskMember;
import com.cruca.task_api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskMemberRepository extends JpaRepository<TaskMember, Long> {

    boolean existsByUserAndProjectTask(User user, ProjectTask projectTask);

    List<TaskMember> findByProjectTaskAndStatus(ProjectTask projectTask, Status status);

}
