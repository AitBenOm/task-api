package com.cloudready.taskapi.adapters.inbound.dto;

import jakarta.validation.constraints.NotBlank;

public record ChangeStatusRequest(@NotBlank String newStatus) {
}
