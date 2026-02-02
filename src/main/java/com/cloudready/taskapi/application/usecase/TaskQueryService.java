package com.cloudready.taskapi.application.usecase;

import com.cloudready.taskapi.application.service.TaskResult;
import com.cloudready.taskapi.application.service.TaskSummary;
import com.cloudready.taskapi.domain.model.Task;
import com.cloudready.taskapi.domain.model.TaskId;
import com.cloudready.taskapi.domain.port.in.GetTaskUseCase;
import com.cloudready.taskapi.domain.port.in.ListTaskUseCase;
import com.cloudready.taskapi.domain.port.out.TaskRepositoryPort;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public class TaskQueryService implements GetTaskUseCase, ListTaskUseCase {

    private TaskRepositoryPort taskRepositoryPort;

    @Override
    public TaskResult getTask(GetTaskQuery query) {
        Task task = taskRepositoryPort.findById(parseTaskId(query.taskId())).orElseThrow(() -> new IllegalArgumentException("Task not found: " + query.taskId()));

        return getTaskResult(task);
    }

    @Override
    public List<TaskSummary> handle() {
        return taskRepositoryPort.findAll().stream()
                .map(t -> new TaskSummary(t.getId().toString(), t.getTitle().value(), t.getStatus().name()))
                .toList();
    }

    private static TaskId parseTaskId(String taskId) {
        return TaskId.of(UUID.fromString(taskId));
    }

    private static TaskResult getTaskResult(Task savedTask) {
        String dueDateIso = savedTask.dueDate().map(Instant::toString).orElse(null);

        TaskResult res = new TaskResult(savedTask.getId().value().toString(), savedTask.getTitle().value(), savedTask.getTaskDesc(), savedTask.getStatus().name(), dueDateIso, Instant.now(), Instant.now());
        return res;
    }
}
