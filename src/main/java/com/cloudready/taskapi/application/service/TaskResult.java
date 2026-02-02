package com.cloudready.taskapi.application.service;

import java.time.Instant;

public record TaskResult(String id,
                         String title,
                         String description,
                         String status,
                         String dueDateIso,
                         Instant createdAt,
                         Instant updatedAt) {
}
