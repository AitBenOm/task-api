package com.cloudready.taskapi.domain.model;

import com.cloudready.taskapi.domain.policy.PolicyException;
import com.cloudready.taskapi.domain.policy.TaskStatusChangePolicy;

import java.time.Instant;
import java.util.Objects;
import java.util.Optional;

public class Task {

    private TaskId id;
    private TaskTitle title;
    private String taskDesc;
    private TaskDueDate dueDate;
    private TaskStatus status;

    private final Instant createdAt;
    private Instant updatedAt;

    public Task(TaskId id, TaskTitle title, String taskDesc,TaskDueDate dueDate, TaskStatus status, Instant createdAt, Instant updatedAt) {
        this.id = Objects.requireNonNull(id);
        this.title = Objects.requireNonNull(title);
        this.taskDesc = Objects.requireNonNull(taskDesc);
        this.dueDate = Objects.requireNonNull(dueDate);
        this.status = Objects.requireNonNull(status);
        this.createdAt = Objects.requireNonNull(createdAt);
        this.updatedAt = Objects.requireNonNull(updatedAt);
    }

    public static Task create(TaskId id, TaskTitle title, String desc,TaskDueDate dueDate, Instant now) {
        Objects.requireNonNull(now, "now must not be null");
        return new Task(id,title,desc,dueDate,TaskStatus.TODO,now,now);
    }

    public  void rescheduleTask(TaskDueDate dueDate, Instant now){
        Objects.requireNonNull(now,"now must be not null");
        this.dueDate = Objects.requireNonNull(dueDate, "newDueDate must not be null");
        touch(now);
    }

    public Optional<Instant> dueDate() {
        return dueDate.value();
    }

    public void rename(String title, Instant now) {
        Objects.requireNonNull(now, "now must not be null");
        this.title = TaskTitle.getTitle(title);
        touch(now);
    }

    public void changeStatus(TaskStatus to, Instant now, TaskStatusChangePolicy policy) {
        Objects.requireNonNull(now, "now must not be null");
        Objects.requireNonNull(policy, "policy must not be null");
        Objects.requireNonNull(to, "newStatus must not be null");

        policy.assertAllowedChange(this.status, to);
        this.status = to;
        touch(now);
    }

    private void touch(Instant now) {
        if (now.isBefore(this.createdAt)) {
            throw new PolicyException("updatedAt must not be before createdAt");
        }
        this.updatedAt = now;
    }

    public TaskId getId() {
        return id;
    }

    public TaskTitle getTitle() {
        return title;
    }

    public String getTaskDesc() {
        return taskDesc;
    }

    public TaskDueDate getDueDate() {
        return dueDate;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public void setTaskDesc(String taskDesc) {
        this.taskDesc = taskDesc;
    }
}
