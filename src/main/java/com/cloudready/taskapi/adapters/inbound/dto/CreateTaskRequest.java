package com.cloudready.taskapi.adapters.inbound.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateTaskRequest(@NotBlank String title, String desc, String dueDate) {
}
