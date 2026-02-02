package com.cloudready.taskapi.domain.port.out;

import java.time.Instant;

public interface ClockPort {
    Instant getNow();
}
