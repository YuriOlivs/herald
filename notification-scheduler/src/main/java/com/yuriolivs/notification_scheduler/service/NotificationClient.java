package com.yuriolivs.notification_scheduler.service;

import com.yuriolivs.notification.shared.domain.notification.dto.NotificationResponseDTO;
import com.yuriolivs.notification.shared.domain.schedule.dto.SchedulePayloadRequestDTO;
import com.yuriolivs.notification.shared.domain.schedule.dto.ScheduledPayloadResponseDTO;
import com.yuriolivs.notification_scheduler.config.SecurityProperties;
import com.yuriolivs.notification_scheduler.domain.notification.dto.NotificationRequestDTO;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Component
public class NotificationClient {
    private final RestTemplate restTemplate;
    private HttpHeaders headers;

    private static final String INTERNAL_KEY_HEADER = "X-Internal-Key";


    public NotificationClient(
            RestTemplate restTemplate,
            SecurityProperties securityProperties
    ) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(INTERNAL_KEY_HEADER, securityProperties.getInternalKey());

        this.restTemplate = restTemplate;
        this.headers = headers;
    }

    public NotificationResponseDTO findById(UUID id) {
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        return restTemplate.exchange(
                "http://localhost:8083/notifications/" + id,
                HttpMethod.GET,
                entity,
                NotificationResponseDTO.class
        ).getBody();

    }

    public NotificationResponseDTO save(NotificationRequestDTO dto) {
        return restTemplate.exchange(
                "http://localhost:8083/notifications/internal",
                HttpMethod.POST,
                buildEntity(dto),
                NotificationResponseDTO.class
        ).getBody();
    }

    public ScheduledPayloadResponseDTO getNotificationPayload(SchedulePayloadRequestDTO dto) {
        return restTemplate.exchange(
                "http://localhost:8083/notifications/internal/payload",
                HttpMethod.POST,
                buildEntity(dto),
                ScheduledPayloadResponseDTO.class
        ).getBody();
    }

    private <T> HttpEntity<T> buildEntity(T body) {
        return new HttpEntity<>(body, headers);
    }
}
