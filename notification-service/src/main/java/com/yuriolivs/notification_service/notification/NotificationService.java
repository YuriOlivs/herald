package com.yuriolivs.notification_service.notification;

import com.yuriolivs.notification.shared.domain.schedule.dto.SchedulePayloadDTO;
import com.yuriolivs.notification.shared.domain.schedule.dto.SchedulePayloadRequestDTO;
import com.yuriolivs.notification.shared.domain.schedule.dto.ScheduledPayloadResponseDTO;
import com.yuriolivs.notification.shared.exceptions.http.HttpBadRequestException;
import com.yuriolivs.notification.shared.exceptions.http.HttpNotFoundException;
import com.yuriolivs.notification_service.notification.domain.NotificationServiceInterface;
import com.yuriolivs.notification_service.notification.domain.dto.NotificationRequestDTO;
import com.yuriolivs.notification_service.notification.domain.entities.Notification;
import com.yuriolivs.notification_service.notification.messaging.producer.NotificationPublisher;
import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

@Service
@AllArgsConstructor
public class NotificationService implements NotificationServiceInterface {
    private NotificationRepository repo;
    private NotificationPublisher publisher;
    private ObjectMapper objectMapper;

    @Override
    public Notification handleNotificationRequest(NotificationRequestDTO dto) throws IOException, MessagingException {
        Optional<Notification> existing = repo.findByIdempotencyKey(dto.idempotencyKey());
        if (existing.isPresent()) {
            return existing.get();
        }

        String jsonPayload = objectMapper.writeValueAsString(dto.payload());
        Notification notification = Notification.fromRequest(dto, jsonPayload);

        repo.save(notification);
        publisher.publish(notification, dto.payload());

        return notification;
    }

    @Override
    public Notification findById(UUID id) {
        Optional<Notification> existing = repo.findById(id);
        if (existing.isEmpty()) {
            throw new HttpNotFoundException("Notification not found.");
        }

        return existing.get();
    }

    @Override
    public Notification save(NotificationRequestDTO dto) {
        Optional<Notification> existing = repo.findByIdempotencyKey(dto.idempotencyKey());
        if (existing.isPresent()) throw new HttpBadRequestException("Notification Request already exists.");

        String jsonPayload = objectMapper.writeValueAsString(dto.payload());

        Notification notification = Notification.fromRequest(dto, jsonPayload);
        return repo.save(notification);
    }

    public ScheduledPayloadResponseDTO getNotificationsPayload(
            SchedulePayloadRequestDTO dto
    ) {
        List<SchedulePayloadDTO> payloads = new ArrayList<>();

        List<Notification> notifications = repo.findAllById(dto.ids());

        for (Notification notification : notifications) {
            Map<String, String> mapPayload = objectMapper
                    .readValue(notification.getPayload(), new TypeReference<Map<String, String>>() {});

            SchedulePayloadDTO payload = new SchedulePayloadDTO(
                    notification.getId(),
                    mapPayload,
                    notification.getChannel()
            );

            payloads.add(payload);
        }

        return new ScheduledPayloadResponseDTO(payloads);
    }
}
