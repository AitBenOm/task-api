package com.cloudready.taskapi.adapters.inbound.rest;

import com.cloudready.taskapi.adapters.inbound.dto.*;
import com.cloudready.taskapi.application.service.TaskResult;
import com.cloudready.taskapi.application.service.TaskSummary;
import com.cloudready.taskapi.domain.port.in.*;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final CreateTaskUseCase createTask;
    private final GetTaskUseCase getTask;
    private final ListTaskUseCase listTasks;
    private final UpdateTaskDetailUseCase updateTask;
    private final RescheduleTaskUseCase rescheduleTask;
    private final ChangeStatusUseCase changeStatus;
    private final ArchiveTaskUseCase archiveTask;


    public TaskController(CreateTaskUseCase createTask, GetTaskUseCase getTask, ListTaskUseCase listTasks, UpdateTaskDetailUseCase updateTask, RescheduleTaskUseCase rescheduleTask, ChangeStatusUseCase changeStatus, ArchiveTaskUseCase archiveTask) {
        this.createTask = createTask;
        this.getTask = getTask;
        this.listTasks = listTasks;
        this.updateTask = updateTask;
        this.rescheduleTask = rescheduleTask;
        this.changeStatus = changeStatus;
        this.archiveTask = archiveTask;
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TaskResponse create(@Valid @RequestBody CreateTaskRequest req) {
        TaskResult result = createTask.handleCreateResult(new CreateTaskUseCase.CreateTaskCommand(
                req.title(),
                req.desc(),
                req.dueDate()
        ));
        return toResponse(result);
    }

    @GetMapping("/{id}")
    public TaskResponse get(@PathVariable String id) {
        TaskResult result = getTask.getTask(new GetTaskUseCase.GetTaskQuery(id));
        return toResponse(result);
    }

    @GetMapping
    public List<TaskSummaryResponse> list() {
        List<TaskSummary> tasks = listTasks.handle();
        return tasks.stream()
                .map(t -> new TaskSummaryResponse(t.taskId(), t.title(), t.status()))
                .toList();
    }

    @PatchMapping("/{id}")
    public TaskResponse update(@PathVariable String id, @RequestBody UpdateTaskRequest req) {
        TaskResult result = updateTask.updateTaskCommand(new UpdateTaskDetailUseCase.UpdateTaskCommand(
                id,
                req.title(),
                req.description()
        ));
        return toResponse(result);
    }

    @PatchMapping("/{id}/due-date")
    public TaskResponse reschedule(@PathVariable String id, @Valid @RequestBody RescheduleTaskRequest req) {
        TaskResult result = rescheduleTask.reschedule(new RescheduleTaskUseCase.RescheduleTaskCommand(
                id,
                req.dueDateIso()
        ));
        return toResponse(result);
    }

    @PatchMapping("/{id}/status")
    public TaskResponse changeStatus(@PathVariable String id, @Valid @RequestBody ChangeStatusRequest req) {
        TaskResult result = changeStatus.changeTaskStatus(new ChangeStatusUseCase.ChangeStatusCommand(
                id,
                req.newStatus()
        ));
        return toResponse(result);
    }

    @PostMapping("/{id}/archive")
    public TaskResponse archive(@PathVariable String id) {
        TaskResult result = archiveTask.archive(new ArchiveTaskUseCase.ArchiveTaskCommand(id));
        return toResponse(result);
    }

    private static TaskResponse toResponse(TaskResult r) {
        return new TaskResponse(
                r.id(),
                r.title(),
                r.description(),
                r.status(),
                r.dueDateIso(),
                r.createdAt(),
                r.updatedAt()
        );
    }
}
