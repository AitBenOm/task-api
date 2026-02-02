package com.cloudready.taskapi.application.usecase;

import com.cloudready.taskapi.application.service.TaskResult;
import com.cloudready.taskapi.domain.model.*;
import com.cloudready.taskapi.domain.policy.TaskStatusChangePolicy;
import com.cloudready.taskapi.domain.port.in.*;
import com.cloudready.taskapi.domain.port.out.ClockPort;
import com.cloudready.taskapi.domain.port.out.IdGeneratorPort;
import com.cloudready.taskapi.domain.port.out.TaskRepositoryPort;

import java.time.Instant;
import java.time.format.DateTimeParseException;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public final class TaskCommandService implements CreateTaskUSeCase,
        UpdateTaskDetailUseCase,
        RescheduleTaskUseCase,
        ChangeStatusUseCase,
        ArchiveTaskUseCase {

    private final ClockPort clock;
    private final IdGeneratorPort idGeneratorPort;
    private final TaskRepositoryPort taskRepositoryPort;
    private final TaskStatusChangePolicy taskStatusChangePolicy;

    public TaskCommandService(ClockPort clock, IdGeneratorPort idGeneratorPort, TaskRepositoryPort taskRepositoryPort, TaskStatusChangePolicy taskStatusChangePolicy) {
        this.clock = clock;
        this.idGeneratorPort = idGeneratorPort;
        this.taskRepositoryPort = taskRepositoryPort;
        this.taskStatusChangePolicy = taskStatusChangePolicy;
    }

    @Override
    public TaskResult archive(ArchiveTaskCommand command) {
        Objects.requireNonNull(command, "ArchiveTaskCommand must not be null");

        Task task = taskRepositoryPort.findById(parseTaskId(command.taskId())).orElseThrow(() -> new IllegalStateException("Task not found: " + command.taskId()));
        task.changeStatus(TaskStatus.ARCHIVED, Instant.now(), taskStatusChangePolicy);

        Task savedTask = taskRepositoryPort.save(task);
        return getTaskResult(savedTask);
    }


    @Override
    public TaskResult changeTaskStatus(ChangeStatusCommand command) {
        Objects.requireNonNull(command, "ChangeStatusCommand must not be null");

        Task task = taskRepositoryPort.findById(parseTaskId(command.taskId())).orElseThrow(() -> new IllegalStateException("Task not found: " + command.taskId()));
        task.changeStatus(TaskStatus.ARCHIVED, Instant.now(), taskStatusChangePolicy);

        Task savedTask = taskRepositoryPort.save(task);
        return getTaskResult(savedTask);
    }

    @Override
    public TaskResult handleCreateResult(CreateTaskCommand command) {
        Objects.requireNonNull(command, "CreateTaskCommand must not be null");


        TaskId id = TaskId.of(idGeneratorPort.generateTaskID());
        TaskTitle title = TaskTitle.getTitle(command.title());
        Instant dueDateInstant = parseIsoInstantOrNull(command.dueDate());
        TaskDueDate dueDate = TaskDueDate.getPossibleDueDate(dueDateInstant, Instant.now());

        Task task = Task.create(id, title, command.taskDesc(), dueDate, Instant.now());
        return getTaskResult(taskRepositoryPort.save(task));
    }


    @Override
    public TaskResult Reschedule(RescheduleTaskCommand command) {
        Objects.requireNonNull(command, "RescheduleTaskCommand must not be null");

        Task task = taskRepositoryPort.findById(parseTaskId(command.taskId())).orElseThrow(() -> new IllegalStateException("Task not found: " + command.taskId()));
        Instant dueDateInstant = parseIsoInstantOrNull(command.dueDate());
        TaskDueDate dueDate = TaskDueDate.getPossibleDueDate(dueDateInstant, Instant.now());
        task.rescheduleTask(dueDate, Instant.now());
        return getTaskResult(taskRepositoryPort.save(task));
    }

    @Override
    public TaskResult updateTaskCommand(UpdateTaskCommand command) {
        Objects.requireNonNull(command, "command must not be null");
        Instant now = clock.getNow();

        Task task = taskRepositoryPort.findById(parseTaskId(command.taskId()))
                .orElseThrow(() -> new IllegalArgumentException("Task not found: " + command.taskId()));

        if (command.title() != null) {
            task.rename(command.title(), now);
        }
        if (command.desc() != null) {
            task.setTaskDesc((command.desc()));
        }

        Task saved = taskRepositoryPort.save(task);
        return getTaskResult(saved);
    }


    private static TaskResult getTaskResult(Task savedTask) {
        String dueDateIso = savedTask.dueDate().map(Instant::toString).orElse(null);

        TaskResult res = new TaskResult(savedTask.getId().value().toString(), savedTask.getTitle().value(), savedTask.getTaskDesc(), savedTask.getStatus().name(), dueDateIso, Instant.now(), Instant.now());
        return res;
    }

    private static TaskId parseTaskId(String taskId) {
        return TaskId.of(UUID.fromString(taskId));
    }

    private static TaskTitle parseTaskTitle(String title) {
        return TaskTitle.getTitle(title);
    }

    private static Instant parseIsoInstantOrNull(String raw) {
        if (raw == null || raw.isBlank()) {
            return null;
        }
        try {
            return Instant.parse(raw.trim());
        } catch (DateTimeParseException ex) {
            throw new IllegalArgumentException("Invalid dueDateIso (expected ISO-8601 Instant): " + raw);
        }
    }
}
