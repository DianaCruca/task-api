package com.cruca.task_api.model;

import com.cruca.task_api.enums.InvitationStatus;
import com.cruca.task_api.enums.ProjectRole;
import com.cruca.task_api.enums.Status;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "t_project_member")
@Data
public class ProjectMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_member_id")
    private Long projectMemberId;

    @Column(name = "project_role", nullable = false)
    @Enumerated(EnumType.STRING)
    private ProjectRole projectRole;

    @Column(name = "joined_at")
    private Date joinedAt;

    @ManyToOne
    @JoinColumn(name = "member_user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

}
