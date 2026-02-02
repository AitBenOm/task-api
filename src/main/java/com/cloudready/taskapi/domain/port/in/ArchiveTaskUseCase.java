package com.cloudready.taskapi.domain.port.in;

import com.cloudready.taskapi.application.service.TaskResult;

public interface ArchiveTaskUseCase {

    TaskResult archive(ArchiveTaskCommand command);

    record ArchiveTaskCommand(String taskId) {}
}
