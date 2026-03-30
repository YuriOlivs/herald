package com.yuriolivs.notification_scheduler.service;

import com.yuriolivs.notification.shared.domain.notification.dto.NotificationResponseDTO;
import com.yuriolivs.notification.shared.domain.schedule.dto.SchedulePayloadRequestDTO;
import com.yuriolivs.notification.shared.domain.schedule.dto.ScheduledPayloadResponseDTO;
import com.yuriolivs.notification_scheduler.config.SecurityProperties;
import com.yuriolivs.notification_scheduler.domain.notification.dto.NotificationRequestDTO;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Component
@AllArgsConstructor
public class NotificationClient {
    @Autowired
    private final RestTemplate restTemplate;
    private final SecurityProperties securityProperties;

    private static final String INTERNAL_KEY_HEADER = "X-Internal-Key";

    public NotificationResponseDTO findById(UUID id) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(INTERNAL_KEY_HEADER, securityProperties.getInternalKey());
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        return restTemplate.exchange(
                "http://localhost:8083/notifications",
                HttpMethod.GET,
                entity,
                NotificationResponseDTO.class
        ).getBody();

    }

    public NotificationResponseDTO save(NotificationRequestDTO dto) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(INTERNAL_KEY_HEADER, securityProperties.getInternalKey());
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        return restTemplate.exchange(
                "http://localhost:8083/notifications/internal",
                HttpMethod.POST,
                entity,
                NotificationResponseDTO.class
        ).getBody();
    }

    public ScheduledPayloadResponseDTO getNotificationPayload(SchedulePayloadRequestDTO dto) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(INTERNAL_KEY_HEADER, securityProperties.getInternalKey());
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        return restTemplate.exchange(
                "http://localhost:8083/notifications/internal/payload",
                HttpMethod.POST,
                entity,
                ScheduledPayloadResponseDTO.class
        ).getBody();
    }
}
