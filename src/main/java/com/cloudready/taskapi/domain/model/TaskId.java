package com.cloudready.taskapi.domain.model;

import java.util.Objects;
import java.util.UUID;

public final class TaskId {
    private final UUID value;

    private TaskId(UUID value) {
        this.value = Objects.requireNonNull(value, "taskId value must not be null");
    }

    public static TaskId of(UUID value) {
        return new TaskId(value);
    }

    public UUID value() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TaskId other)) return false;
        return value.equals(other.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
