package com.cloudready.taskapi.domain.model;

import java.time.Instant;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public class TaskDueDate{

 private final Instant dueDate;

    public TaskDueDate(Instant dueDate) {
        this.dueDate = dueDate;
    }

    public static TaskDueDate getPossibleDueDate(Instant dueDate, Instant now){
        Objects.requireNonNull(now, "now must not be null");

        if (dueDate == null) {
            return new TaskDueDate(null);
        }

        if (dueDate.isBefore(now)) {
            throw new IllegalArgumentException("due date must not be in the past");
        }

        return new TaskDueDate(dueDate);
    }

    public Optional<Instant> value() {
        return Optional.ofNullable(dueDate);
    }
}
