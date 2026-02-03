package com.cloudready.taskapi.domain.port.in;

import com.cloudready.taskapi.application.service.TaskResult;

public interface CreateTaskUseCase {

    TaskResult handleCreateResult(CreateTaskCommand command);
    record CreateTaskCommand(String title, String taskDesc, String dueDate){};
}
