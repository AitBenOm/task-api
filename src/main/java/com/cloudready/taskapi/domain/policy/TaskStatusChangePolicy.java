package com.cloudready.taskapi.domain.policy;

import com.cloudready.taskapi.domain.model.TaskStatus;

public final class TaskStatusChangePolicy {
    public void assertAllowedChange(TaskStatus from, TaskStatus to) {
        if (from == null || to == null) {
            throw new PolicyException("Task status transition requires non-null statuses");
        }
        if (from == to) {
            return;
        }
        boolean allowed = switch (from) {
            case TODO -> (to == TaskStatus.IN_PROGRESS) || (to == TaskStatus.ARCHIVED);
            case ARCHIVED -> false;
            case DONE ->to==TaskStatus.ARCHIVED;
            case IN_PROGRESS -> (to == TaskStatus.ARCHIVED) || (to == TaskStatus.DONE);
        };

        if(!allowed){
            throw new PolicyException("Invalid status transition: " + from + " -> " + to);
        }
    }
}
