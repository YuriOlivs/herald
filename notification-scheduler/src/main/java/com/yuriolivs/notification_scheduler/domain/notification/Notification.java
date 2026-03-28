package com.yuriolivs.notification_scheduler.domain.notification;

import com.yuriolivs.notification.shared.domain.notification.enums.NotificationChannel;
import com.yuriolivs.notification.shared.domain.notification.enums.NotificationPriority;
import com.yuriolivs.notification.shared.domain.notification.enums.NotificationStatus;
import com.yuriolivs.notification.shared.domain.notification.enums.NotificationType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
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
}
