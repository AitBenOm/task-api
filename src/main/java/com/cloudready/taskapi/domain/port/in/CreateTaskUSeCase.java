package com.cloudready.taskapi.domain.port.in;

import com.cloudready.taskapi.application.service.TaskResult;
import com.cloudready.taskapi.domain.model.TaskDueDate;
import com.cloudready.taskapi.domain.model.TaskTitle;

public interface CreateTaskUSeCase {

    TaskResult handleCreateResult(CreateTaskCommand command);
    record CreateTaskCommand(String title, String taskDesc, String dueDate){};
}
