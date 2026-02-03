package com.cloudready.taskapi.adapters.outbound.infrastructure;
import com.cloudready.taskapi.adapters.outbound.persistence.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

public interface TaskJpaRepository extends JpaRepository<TaskEntity, UUID> {

}
