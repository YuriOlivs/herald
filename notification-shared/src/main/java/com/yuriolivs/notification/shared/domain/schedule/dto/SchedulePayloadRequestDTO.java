package com.yuriolivs.notification.shared.domain.schedule.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

public record SchedulePayloadRequestDTO(
        @NotEmpty
        @NotNull
        List<UUID> ids
) { }
