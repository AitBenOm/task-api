package com.cloudready.taskapi.adapters.outbound.persistence;

import com.cloudready.taskapi.domain.port.out.IdGeneratorPort;

import java.util.UUID;

public class IdGeneratorAdapter implements IdGeneratorPort {
    @Override
    public UUID generateTaskID() {
        return UUID.randomUUID();
    }
}
