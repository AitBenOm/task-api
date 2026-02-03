package com.cloudready.taskapi.config;

import com.cloudready.taskapi.application.usecase.TaskCommandService;
import com.cloudready.taskapi.application.usecase.TaskQueryService;
import com.cloudready.taskapi.domain.policy.TaskStatusChangePolicy;
import com.cloudready.taskapi.domain.port.out.ClockPort;
import com.cloudready.taskapi.domain.port.out.IdGeneratorPort;
import com.cloudready.taskapi.domain.port.out.TaskRepositoryPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfig {

    @Bean
    public TaskCommandService taskCommandService(TaskRepositoryPort repo,
                                                 IdGeneratorPort idGen,
                                                 ClockPort clock,
                                                 TaskStatusChangePolicy policy) {
        return new TaskCommandService(clock, idGen, repo, policy);
    }

    @Bean
    public TaskQueryService taskQueryService(TaskRepositoryPort repo) {
        return new TaskQueryService(repo);
    }
}
