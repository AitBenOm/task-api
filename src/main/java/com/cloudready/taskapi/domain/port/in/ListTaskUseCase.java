package com.cloudready.taskapi.domain.port.in;

import com.cloudready.taskapi.application.service.TaskSummary;

import java.util.List;

public interface ListTaskUseCase {

    List<TaskSummary> handle();
}
