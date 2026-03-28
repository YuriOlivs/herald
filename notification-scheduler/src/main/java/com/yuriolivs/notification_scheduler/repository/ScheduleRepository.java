package com.yuriolivs.notification_scheduler.repository;

import com.yuriolivs.notification_scheduler.domain.schedule.entities.ScheduledNotification;
import com.yuriolivs.notification.shared.domain.schedule.enums.ScheduleStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ScheduleRepository extends JpaRepository<ScheduledNotification, UUID> {
    Optional<ScheduledNotification> findById(UUID id);
    List<ScheduledNotification> findAll();
    List<ScheduledNotification> findAllByScheduledAt(LocalDateTime scheduledAt);
    Optional<ScheduledNotification> findByIdempotencyKey(String idempotencyKey);
    Optional<ScheduledNotification> findByNotificationId(UUID id);
    List<ScheduledNotification> findByStatusAndIsActiveTrueAndScheduledAtBetween(
            ScheduleStatus status,
            LocalDateTime start,
            LocalDateTime end
    );
}
