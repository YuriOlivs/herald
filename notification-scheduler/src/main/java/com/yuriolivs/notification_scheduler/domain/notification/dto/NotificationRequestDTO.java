package com.yuriolivs.notification_scheduler.domain.notification.dto;

import com.yuriolivs.notification.shared.domain.notification.enums.NotificationChannel;
import com.yuriolivs.notification.shared.domain.notification.enums.NotificationPriority;
import com.yuriolivs.notification.shared.domain.notification.enums.NotificationType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Map;

public record NotificationRequestDTO(
    @NotBlank(message = "Idempotency Key must be filled.")
    String idempotencyKey,

    @NotBlank(message = "Channel must be filled.")
    NotificationChannel channel,

    @NotBlank(message = "Recipient must be filled.")
    String recipient,

    NotificationType type,

    String template,

    NotificationPriority priority,

    @NotNull(message = "Payload must not be null.")
    Map<String, String> payload
) {
}
