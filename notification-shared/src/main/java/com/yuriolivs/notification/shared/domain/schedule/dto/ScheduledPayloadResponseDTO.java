package com.yuriolivs.notification.shared.domain.schedule.dto;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public record ScheduledPayloadResponseDTO(
        @NotNull
        List<SchedulePayloadDTO> list
) {}
