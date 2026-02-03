package com.cloudready.taskapi.adapters.inbound.dto;

import java.time.Instant;

public record TaskResponse(String id,
                           String title,
                           String description,
                           String status,
                           String dueDateIso,
                           Instant createdAt,
                           Instant updatedAt) {
}
