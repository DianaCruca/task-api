package com.cruca.task_api.repository;

import com.cruca.task_api.model.TaskMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskMemberRepository extends JpaRepository<TaskMember, Long> {
}
