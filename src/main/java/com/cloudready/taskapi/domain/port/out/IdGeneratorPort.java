package com.cloudready.taskapi.domain.port.out;

import java.util.UUID;

public interface IdGeneratorPort {
    public UUID generateTaskID();
}
