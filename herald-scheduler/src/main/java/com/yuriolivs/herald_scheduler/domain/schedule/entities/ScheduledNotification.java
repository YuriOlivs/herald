package com.yuriolivs.herald_scheduler.domain.schedule.entities;

import com.yuriolivs.notification.shared.domain.notification.enums.NotificationChannel;
import com.yuriolivs.notification.shared.domain.schedule.enums.ScheduleStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(
        name = "notifications_scheduled",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_scheduled_idempotency",
                        columnNames = "idempotencyKey"
                )
        }
)
@ToString
public class ScheduledNotification {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String idempotencyKey;

    @Column(nullable = false)
    private UUID notificationId;

    private String recipient;

    @Enumerated(EnumType.STRING)
    private NotificationChannel channel;

    private Boolean isActive;

    @Enumerated(EnumType.STRING)
    private ScheduleStatus status;

    private LocalDateTime scheduledAt;

    private UUID tenantId;

    public ScheduledNotification(
            String idempotencyKey,
            UUID notificationId,
            String recipient,
            NotificationChannel channel,
            Boolean isActive,
            ScheduleStatus status,
            LocalDateTime scheduledAt,
            UUID tenantId
    ) {
        this.idempotencyKey = idempotencyKey;
        this.notificationId = notificationId;
        this.recipient = recipient;
        this.channel = channel;
        this.isActive = isActive;
        this.status = status;
        this.scheduledAt = scheduledAt;
        this.tenantId = tenantId;
    }
}
