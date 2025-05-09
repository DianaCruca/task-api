package com.cruca.task_api.repository;

import com.cruca.task_api.model.Project;
import com.cruca.task_api.model.ProjectMember;
import com.cruca.task_api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface ProjectMemberRepository extends JpaRepository<ProjectMember, Long> {

    Set<ProjectMember> findByUser(User user);

    boolean existsByUserAndProject(User user, Project project);

}
