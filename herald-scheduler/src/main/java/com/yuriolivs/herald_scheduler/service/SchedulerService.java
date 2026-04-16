package com.yuriolivs.herald_scheduler.service;

import com.yuriolivs.herald_scheduler.client.NotificationClient;
import com.yuriolivs.notification.shared.domain.notification.NotificationMessage;
import com.yuriolivs.notification.shared.domain.notification.dto.NotificationResponseDTO;
import com.yuriolivs.notification.shared.domain.notification.enums.NotificationPriority;
import com.yuriolivs.notification.shared.domain.schedule.dto.SchedulePayloadDTO;
import com.yuriolivs.notification.shared.domain.schedule.dto.SchedulePayloadRequestDTO;
import com.yuriolivs.notification.shared.domain.schedule.dto.ScheduledPayloadResponseDTO;
import com.yuriolivs.notification.shared.exceptions.http.HttpBadRequestException;
import com.yuriolivs.notification.shared.exceptions.http.HttpNotFoundException;
import com.yuriolivs.herald_scheduler.domain.schedule.dto.ScheduleRequestDTO;
import com.yuriolivs.herald_scheduler.domain.schedule.entities.ScheduledNotification;
import com.yuriolivs.notification.shared.domain.schedule.enums.ScheduleStatus;
import com.yuriolivs.herald_scheduler.domain.schedule.interfaces.SchedulerServiceInterface;
import com.yuriolivs.herald_scheduler.repository.ScheduleRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import tools.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;
import java.util.*;

@Service
@AllArgsConstructor
public class SchedulerService implements SchedulerServiceInterface {
    @Autowired
    private final ScheduleRepository repo;

    @Autowired
    private final NotificationClient client;

    @Override
    public ScheduledNotification findScheduledNotification(UUID id) {
        Optional<ScheduledNotification> existing = repo.findById(id);
        if (existing.isEmpty()) throw new HttpNotFoundException("Schedule not found.");

        return existing.get();
    }

    @Override
    public ScheduledNotification scheduleMessage(ScheduleRequestDTO dto, UUID tenantId) {
        Optional<ScheduledNotification> existing = repo.findByIdempotencyKey(dto.idempotencyKey());
        if (existing.isPresent())
            return existing.get();

        try {
            NotificationResponseDTO savedNotification = client
                    .save(dto.notification());

            ScheduledNotification scheduledNotification = new ScheduledNotification(
                    dto.idempotencyKey(),
                    savedNotification.id(),
                    savedNotification.recipient(),
                    savedNotification.channel(),
                    true,
                    ScheduleStatus.SCHEDULED,
                    dto.dateTime(),
                    tenantId
            );

            return repo.save(scheduledNotification);
        } catch (HttpClientErrorException.BadRequest ex) {
            throw new HttpBadRequestException(ex.getMessage());
        }
    }

    @Override
    public ScheduledNotification updateStatus(ScheduleStatus status, UUID notificationId) {
        Optional<ScheduledNotification> notificationFound = repo.findByNotificationId(notificationId);
        if (notificationFound.isEmpty()) throw new HttpBadRequestException("Scheduled Notification not found.");

        ScheduledNotification notification = notificationFound.get();
        notification.setStatus(status);

        return repo.save(notification);
    }

    @Override
    public List<ScheduledNotification> findAllScheduledMessages() {
        return repo.findAll();
    }

    @Override
    public List<ScheduledNotification> findAllScheduledMessagesByDate(LocalDateTime date) {
        return repo.findAllByScheduledAt(date);
    }

    @Override
    public List<NotificationMessage> findNotificationsToBeProcessed(
            LocalDateTime startOfDay,
            LocalDateTime now
    ) {
        System.out.println(startOfDay);
        System.out.println(now);

        List<NotificationMessage> notificationsToBeSent = new ArrayList<>();

        List<ScheduledNotification> scheduledNotifications = repo
                .findByStatusAndIsActiveTrueAndScheduledAtBetween(
                        ScheduleStatus.SCHEDULED,
                        startOfDay,
                        now
                );

        if (scheduledNotifications.isEmpty()) return notificationsToBeSent;

        List<UUID> ids = scheduledNotifications
                .stream()
                .map(ScheduledNotification::getNotificationId)
                .toList();

        SchedulePayloadRequestDTO request = new SchedulePayloadRequestDTO(ids);
        ScheduledPayloadResponseDTO response = client.getNotificationPayload(request);

        for (SchedulePayloadDTO payload : response.list()) {

            UUID scheduleId = scheduledNotifications
                    .stream()
                    .filter(notification -> notification.getNotificationId().equals(payload.id()))
                    .map(ScheduledNotification::getId)
                    .findFirst()
                    .orElse(null);

            NotificationMessage send = new NotificationMessage(
                    payload.id(),
                    NotificationPriority.SCHEDULED,
                    payload.payload(),
                    payload.channel(),
                    scheduleId
            );

            notificationsToBeSent.add(send);
        }

        this.updateStatusBatch(ScheduleStatus.PROCESSING, scheduledNotifications);

        return notificationsToBeSent;
    }

    @Override
    public ScheduledNotification save(ScheduledNotification notification) {
        return repo.save(notification);
    }

    @Override
    public ScheduledNotification cancelSchedule(UUID id) {
        ScheduledNotification notification = this.findScheduledNotification(id);
        notification.setIsActive(false);

        return repo.save(notification);
    }

    public void updateStatusBatch(ScheduleStatus status, List<ScheduledNotification> notifications) {
        if (notifications.isEmpty()) return;

        for (ScheduledNotification notification : notifications) {
            notification.setStatus(status);

            if (!status.equals(ScheduleStatus.SCHEDULED)) {
                notification.setIsActive(false);
            }
        }

        repo.saveAll(notifications);
    }

}
