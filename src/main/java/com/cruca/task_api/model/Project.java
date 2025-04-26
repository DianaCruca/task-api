package com.cruca.task_api.model;

import com.cruca.task_api.enums.Status;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "t_project")
@Data
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_id")
    private Long projectId;

    @Column(name = "name", nullable = false)
    private String name;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", updatable = false)
    private Date createdAt;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at", updatable = false)
    private Date updatedAt;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

    @OneToOne
    @JoinColumn(name = "created_by")
    private User createdBy;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "project", orphanRemoval = true)
    private Set<ProjectTask> projectTask;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "project", orphanRemoval = true)
    private Set<ProjectMember> projectMember;

}
