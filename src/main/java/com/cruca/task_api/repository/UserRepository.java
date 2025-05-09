package com.cruca.task_api.repository;

import com.cruca.task_api.enums.Status;
import com.cruca.task_api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);

    Optional<User> existsByIdAndStatus(Long id, Status status);

    Optional<User> findByEmail(String email);

    Optional<User> findByEmailAndStatus(String email, Status status);

    List<User> findByStatus(Status status);

}
