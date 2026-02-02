package com.cloudready.taskapi.adapters.outbound.persistence;

import com.cloudready.taskapi.domain.port.out.ClockPort;

import java.time.Instant;

public class ClockAdapter implements ClockPort {
    @Override
    public Instant getNow() {
        return Instant.now();
    }
}
