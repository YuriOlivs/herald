package com.yuriolivs.notification_scheduler.service;

import com.yuriolivs.notification.shared.domain.notification.dto.NotificationResponseDTO;
import com.yuriolivs.notification.shared.domain.schedule.dto.SchedulePayloadRequestDTO;
import com.yuriolivs.notification.shared.domain.schedule.dto.ScheduledPayloadResponseDTO;
import com.yuriolivs.notification_scheduler.domain.notification.dto.NotificationRequestDTO;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Component
@AllArgsConstructor
public class NotificationClient {
    @Autowired
    private final RestTemplate restTemplate;

    public NotificationResponseDTO findById(UUID id) {
        return restTemplate.getForObject(
                "http://localhost:8080/notifications",
                NotificationResponseDTO.class
        );
    }

    public NotificationResponseDTO save(NotificationRequestDTO dto) {
        return restTemplate.postForObject(
                "http://localhost:8080/notifications/internal",
                dto,
                NotificationResponseDTO.class
        );
    }

    public ScheduledPayloadResponseDTO getNotificationPayload(SchedulePayloadRequestDTO dto) {
        return restTemplate.postForObject(
                "http://localhost:8080/notifications/internal/payload",
                dto,
                ScheduledPayloadResponseDTO.class
        );
    }
}
