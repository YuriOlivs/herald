package com.yuriolivs.notification.shared.domain.notification.dto;

import com.yuriolivs.notification.shared.domain.notification.entities.Notification;
import com.yuriolivs.notification.shared.domain.notification.enums.NotificationChannel;
import com.yuriolivs.notification.shared.domain.notification.enums.NotificationStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public record NotificationResponseDTO(
        UUID id,
        NotificationStatus status,
        NotificationChannel channel,
        String recipient,
        LocalDateTime createdAt
) {
    public static NotificationResponseDTO from(Notification entity) {
        return new NotificationResponseDTO(
                entity.getId(),
                entity.getStatus(),
                entity.getChannel(),
                entity.getRecipient(),
                entity.getCreatedAt()
        );
    }
}
