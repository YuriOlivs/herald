package com.yuriolivs.notification.shared.domain.notification;

import com.yuriolivs.notification.shared.domain.notification.enums.NotificationChannel;
import com.yuriolivs.notification.shared.domain.notification.enums.NotificationPriority;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;
import java.util.UUID;

@Getter
@Setter
public class NotificationMessage {
    private UUID id;
    private NotificationPriority priority;
    private Map<String, String> payload;
    private NotificationChannel channel;
    private UUID scheduleId;

    public NotificationMessage() {}

    public NotificationMessage(
            UUID id,
            NotificationPriority priority,
            Map<String, String> payload,
            NotificationChannel channel
    ) {
        this.id = id;
        this.priority = priority;
        this.payload = payload;
        this.channel = channel;
    }

    public NotificationMessage(
            UUID id,
            NotificationPriority priority,
            Map<String, String> payload,
            NotificationChannel channel,
            UUID scheduleId
    ) {
        this.id = id;
        this.priority = priority;
        this.payload = payload;
        this.channel = channel;
        this.scheduleId = scheduleId;
    }
}
