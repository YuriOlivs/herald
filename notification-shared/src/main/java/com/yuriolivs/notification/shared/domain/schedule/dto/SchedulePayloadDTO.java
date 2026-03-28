package com.yuriolivs.notification.shared.domain.schedule.dto;

import com.yuriolivs.notification.shared.domain.notification.enums.NotificationChannel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Map;
import java.util.UUID;

public record SchedulePayloadDTO(
        @NotNull
        UUID id,

        @NotBlank
        Map<String, String> payload,

        NotificationChannel channel
) {}
