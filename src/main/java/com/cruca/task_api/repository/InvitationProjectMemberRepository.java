package com.cruca.task_api.repository;

import com.cruca.task_api.enums.Status;
import com.cruca.task_api.model.InvitationProjectMember;
import com.cruca.task_api.model.Project;
import com.cruca.task_api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvitationProjectMemberRepository extends JpaRepository<InvitationProjectMember, Long> {

    List<InvitationProjectMember> findByUserAndStatus(User user, Status status);

    List<InvitationProjectMember> findByProjectAndStatus(Project project, Status status);

    boolean existsByUserAndProjectAndStatus(User user, Project project, Status status);

}
