package com.cloudready.taskapi.config;

import com.cloudready.taskapi.adapters.outbound.infrastructure.TaskJpaRepository;
import com.cloudready.taskapi.adapters.outbound.persistence.ClockAdapter;
import com.cloudready.taskapi.adapters.outbound.persistence.IdGeneratorAdapter;
import com.cloudready.taskapi.adapters.outbound.persistence.TaskRepositoryAdapter;
import com.cloudready.taskapi.domain.port.out.ClockPort;
import com.cloudready.taskapi.domain.port.out.IdGeneratorPort;
import com.cloudready.taskapi.domain.port.out.TaskRepositoryPort;
import org.springframework.context.annotation.Bean;

public class PersistenceConfig {

    @Bean
    TaskRepositoryPort taskRepositoryPort(TaskJpaRepository jpaRepository) {
        return new TaskRepositoryAdapter(jpaRepository);
    }

    @Bean
    ClockPort clockPort() {
        return new ClockAdapter();
    }

    @Bean
    IdGeneratorPort idGeneratorPort() {
        return new IdGeneratorAdapter();
    }
}
