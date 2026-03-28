package com.yuriolivs.notification_service.notification.domain.entities;

import com.yuriolivs.notification.shared.domain.notification.enums.NotificationChannel;
import com.yuriolivs.notification.shared.domain.notification.enums.NotificationPriority;
import com.yuriolivs.notification.shared.domain.notification.enums.NotificationStatus;
import com.yuriolivs.notification.shared.domain.notification.enums.NotificationType;
import com.yuriolivs.notification_service.notification.domain.dto.NotificationRequestDTO;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(
        name = "notifications",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_notification_idempotency",
                        columnNames = "idempotencyKey"
                )
        }
)
@NoArgsConstructor
@ToString
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String idempotencyKey;

    private NotificationChannel channel;

    private String recipient;

    private String template;

    private NotificationType type;

    @Enumerated(EnumType.STRING)
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

    public static Notification fromRequest(NotificationRequestDTO dto, String payload) {
        return new Notification(
                dto.idempotencyKey(),
                dto.channel(),
                dto.recipient(),
                dto.template().name(),
                dto.type(),
                NotificationStatus.CREATED,
                dto.priority(),
                LocalDateTime.now(),
                payload
        );
    }
}
