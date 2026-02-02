package com.cloudready.taskapi.adapters.outbound.persistence;

import com.cloudready.taskapi.adapters.outbound.infrastructure.TaskJpaRepository;
import com.cloudready.taskapi.domain.model.Task;
import com.cloudready.taskapi.domain.model.TaskId;
import com.cloudready.taskapi.domain.port.out.TaskRepositoryPort;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class TaskRepositoryAdapter implements TaskRepositoryPort {

    private TaskJpaRepository taskJpaRepository;

    public TaskRepositoryAdapter(TaskJpaRepository taskJpaRepository) {
        this.taskJpaRepository = taskJpaRepository;
    }

    @Override
    public Task save(Task t) {
        TaskEntity entity = TaskMapper.toTaskEntity(t);
        return TaskMapper.toTaskDomain(this.taskJpaRepository.save(entity));
    }

    @Override
    public Optional<Task> findById(TaskId id) {
        return this.taskJpaRepository.findById(id.value()).map(TaskMapper::toTaskDomain);

    }

    @Override
    public List<Task> findAll() {
        return this.taskJpaRepository.findAll().stream().map(TaskMapper::toTaskDomain).collect(Collectors.toList());
    }
}
