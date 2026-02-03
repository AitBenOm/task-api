package com.cloudready.taskapi.adapters.outbound.persistence;

import com.cloudready.taskapi.domain.model.*;

import java.time.Instant;
import java.time.format.DateTimeParseException;
import java.util.UUID;

public class TaskMapper {

    public static TaskEntity toTaskEntity(Task task){
        TaskEntity entity = new TaskEntity();
        entity.setCreatedAt(task.getCreatedAt());
        entity.setTitle(task.getTitle().value());
        entity.setDescription(task.getTaskDesc());
        entity.setDueDate(task.getDueDate().value().get());
        entity.setUpdatedAt(task.getUpdatedAt());
        entity.setStatus(task.getStatus().name());
        entity.setId(task.getId().value());
        return entity;
    }
    public static Task toTaskDomain(TaskEntity taskEntity){
        TaskId id = parseTaskId(String.valueOf(taskEntity.getId()));
        TaskTitle title = parseTaskTitle(taskEntity.getTitle());
        TaskDueDate possibleDueDate = TaskDueDate.getPossibleDueDate(taskEntity.getDueDate(), Instant.now());
        TaskStatus status = TaskStatus.valueOf(taskEntity.getStatus());

        Task businessTask = new Task(
                id,
                title,
                taskEntity.getDescription(),
                possibleDueDate,
                status,
                taskEntity.getCreatedAt(),
                taskEntity.getUpdatedAt());

        return businessTask;
    }

    private static TaskTitle parseTaskTitle(String title) {
        return TaskTitle.getTitle(title);
    }
    private static TaskId parseTaskId(String taskId) {
        return TaskId.of(UUID.fromString(taskId));
    }
}
