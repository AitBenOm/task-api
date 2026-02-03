package com.cloudready.taskapi.adapters.inbound.dto;

public record TaskSummaryResponse(   String id,
                                     String title,
                                     String status) {
}
