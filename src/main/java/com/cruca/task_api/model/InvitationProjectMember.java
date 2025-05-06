package com.cruca.task_api.model;

import com.cruca.task_api.enums.InvitationStatus;
import com.cruca.task_api.enums.ProjectRole;
import com.cruca.task_api.enums.Status;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Entity
@Table(name = "t_invitation_project_member")
@Data
public class InvitationProjectMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "invitation_project_member_id")
    private Long invitationProjectMemberId;

    @Column(name = "invitation_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private InvitationStatus invitationStatus;

    @Column(name = "project_role", nullable = false)
    @Enumerated(EnumType.STRING)
    private ProjectRole projectRole;

    @Column(name = "date_sent")
    private Date dateSent;

    @ManyToOne
    @JoinColumn(name = "member_user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "update_at", nullable = true, insertable = false)
    private Date updatedAt;

}
