package com.yuriolivs.notification.shared.domain.notification.entities;

import com.yuriolivs.notification.shared.domain.notification.enums.NotificationChannel;
import com.yuriolivs.notification.shared.domain.notification.enums.NotificationPriority;
import com.yuriolivs.notification.shared.domain.notification.enums.NotificationStatus;
import com.yuriolivs.notification.shared.domain.notification.enums.NotificationType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.UUID;

@ToString
@Getter
@Setter
public class Notification {
    private UUID id;

    private String idempotencyKey;

    private NotificationChannel channel;

    private String recipient;

    private String template;

    private NotificationType type;

    private NotificationStatus status;

    private NotificationPriority priority;

    private LocalDateTime createdAt;

    private String payload;

    public Notification(
        String idempotencyKey,
        NotificationChannel channel,
        String recipient,
        String template,
        NotificationType type,
        NotificationStatus status,
        NotificationPriority priority,
        LocalDateTime createdAt,
        String payload
    ) {
        this.idempotencyKey = idempotencyKey;
        this.channel = channel;
        this.recipient = recipient;
        this.template = template;
        this.type = type;
        this.status = status;
        this.priority = priority;
        this.createdAt = createdAt;
        this.payload = payload;
    }
}
