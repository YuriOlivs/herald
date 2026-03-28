package com.yuriolivs.notification_scheduler.domain.schedule.interfaces;

import com.yuriolivs.notification.shared.domain.notification.NotificationMessage;
import com.yuriolivs.notification_scheduler.domain.schedule.dto.ScheduleRequestDTO;
import com.yuriolivs.notification_scheduler.domain.schedule.entities.ScheduledNotification;
import com.yuriolivs.notification.shared.domain.schedule.enums.ScheduleStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface SchedulerServiceInterface {
    ScheduledNotification findScheduledNotification(UUID id);
    ScheduledNotification scheduleMessage(ScheduleRequestDTO dto);
    ScheduledNotification updateStatus(ScheduleStatus status, UUID notificationId);
    List<ScheduledNotification> findAllScheduledMessages();
    List<ScheduledNotification> findAllScheduledMessagesByDate(LocalDateTime date);
    List<NotificationMessage> findNotificationsToBeProcessed(
            LocalDateTime startOfDay,
            LocalDateTime now
    );
    ScheduledNotification save(ScheduledNotification notification);
    ScheduledNotification cancelSchedule(UUID id);
}
