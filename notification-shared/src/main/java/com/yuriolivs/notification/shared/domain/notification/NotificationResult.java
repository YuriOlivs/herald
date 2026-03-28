package com.yuriolivs.notification.shared.domain.notification;

import com.yuriolivs.notification.shared.domain.schedule.enums.ScheduleStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@ToString
@Getter
@Setter
public class NotificationResult {
    private UUID id;
    private UUID scheduleId;
    private ScheduleStatus status;
    private String message;

    public NotificationResult() {}

    public NotificationResult(
            UUID id,
            UUID scheduleId,
            ScheduleStatus status,
            String message
    ) {
        this.id = id;
        this.scheduleId = scheduleId;
        this.message = message;
        this.status = status;
    }

    public NotificationResult(
            UUID id,
            UUID scheduleId
    ) {
        this.id = id;
        this.scheduleId = scheduleId;
    }

    public static NotificationResult from(NotificationMessage message) {
        return new NotificationResult(
                message.getId(),
                message.getScheduleId()
        );
    }
}
