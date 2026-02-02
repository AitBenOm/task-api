package com.cloudready.taskapi.domain.port.in;

import com.cloudready.taskapi.application.service.TaskResult;

public interface GetTaskUseCase {

    TaskResult getTask(GetTaskQuery query);
    record GetTaskQuery(String taskId){};
}
