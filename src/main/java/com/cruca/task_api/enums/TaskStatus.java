package com.cruca.task_api.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TaskStatus {

    /// Pending | Pendiente – The task has been created but has not started yet.
    PENDIENTE("Pendiente"),

    /// In_progress | Progreso – Someone is actively working on the task.
    EN_PROGRESO("En Progreso"),

    /// On_hold | En espera – Temporarily paused due to a blocker or decision.
    EN_ESPERA("En Espera"),

    /// On_review  | En revision– The task has been completed but is under review.
    EN_REVISION("En Revisión"),

    /// Completed | Completada – Successfully finished.
    COMPLETADA("Completada"),

    /// Cancelled | Cancelada– Cancelled; it will not be carried out.
    CANCELADA("Cancelada"),

    /// Reopened | Reabierto – Was previously marked as completed but has been reopened.
    REABIERTO("Reabierto");

    private final String statusCode;

}