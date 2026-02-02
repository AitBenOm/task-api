package com.cloudready.taskapi.domain.model;

import java.util.Objects;
import java.util.UUID;

public class TaskTitle {
    private final String title;

    private TaskTitle(String title) {
        this.title = Objects.requireNonNull(title, "TaskTitle vlue must not be null");
    }

    public static TaskTitle getTitle(String title) {
        return new TaskTitle(title);
    }

    public String value() {
        return title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TaskId other)) return false;
        return title.equals(other.value());
    }

    @Override
    public int hashCode() {
        return title.hashCode();
    }

    @Override
    public String toString() {
        return title.toString();
    }
}
