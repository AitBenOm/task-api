package com.cloudready.taskapi.adapters.inbound.dto;

import jakarta.validation.constraints.NotBlank;

public record RescheduleTaskRequest(@NotBlank String dueDateIso
) {
}
