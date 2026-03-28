package com.yuriolivs.notification_scheduler.domain.schedule.dto;

import com.yuriolivs.notification_scheduler.domain.notification.dto.NotificationRequestDTO;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record ScheduleRequestDTO(
        @NotBlank(message = "Idempotency Key must be filled.")
        String idempotencyKey,

        @NotNull(message = "Notification field must be filled with the notification body.")
        NotificationRequestDTO notification,

        @Future(message = "Date to be scheduled must be a future date.")
        LocalDateTime dateTime
) {}
