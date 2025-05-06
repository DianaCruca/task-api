package com.cruca.task_api.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "t_task_member")
@Data
public class TaskMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_member_id")
    private Long taskMemberId;

    @Column(name = "date_assigned")
    private Date dateAssigned;

    @ManyToOne
    @JoinColumn(name = "task_id")
    private ProjectTask projectTask;

    @ManyToOne
    @JoinColumn(name = "assigned_to")
    private User user;

}
