package com.yuriolivs.notification_service.notification.domain.dto;

import com.yuriolivs.notification.shared.domain.notification.enums.NotificationChannel;
import com.yuriolivs.notification.shared.domain.notification.enums.NotificationStatus;
import com.yuriolivs.notification_service.notification.domain.entities.Notification;

import java.time.LocalDateTime;
import java.util.UUID;

public record NotificationResponseDTO(
        UUID id,
        NotificationStatus status,
        NotificationChannel channel,
        LocalDateTime createdAt
) {
    public static NotificationResponseDTO from(Notification entity) {
        return new NotificationResponseDTO(
                entity.getId(),
                entity.getStatus(),
                entity.getChannel(),
                entity.getCreatedAt()
        );
    }
}
