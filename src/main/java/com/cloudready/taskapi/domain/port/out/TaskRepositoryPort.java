package com.cloudready.taskapi.domain.port.out;

import com.cloudready.taskapi.domain.model.Task;
import com.cloudready.taskapi.domain.model.TaskId;

import java.util.List;
import java.util.Optional;

public interface TaskRepositoryPort {
    Task save(Task t);
    Optional<Task> findById(TaskId id);
    List<Task> findAll();
}
