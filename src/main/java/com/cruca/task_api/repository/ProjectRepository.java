package com.cruca.task_api.repository;

import com.cruca.task_api.enums.Status;
import com.cruca.task_api.model.Project;
import com.cruca.task_api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    List<Project> findByStatusAndProjectMemberUser(Status status, User user);

    List<Project> findByStatus(Status status);

}
