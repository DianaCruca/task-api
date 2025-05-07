package com.cruca.task_api.repository;

import com.cruca.task_api.enums.Status;
import com.cruca.task_api.enums.TaskPriority;
import com.cruca.task_api.model.Project;
import com.cruca.task_api.model.ProjectTask;
import com.cruca.task_api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectTaskRepository extends JpaRepository<ProjectTask, Long> {

    List<ProjectTask> findByProjectAndStatus(Project project, Status status);

    List<ProjectTask> findByTaskMemberUser(User user);

    List<ProjectTask> findByProjectAndTaskPriority(Project project, TaskPriority taskPriority);

    List<ProjectTask> findByProjectAndTaskPriorityAndTaskMemberUser(Project project, TaskPriority taskPriority, User user);

}
