package com.yuriolivs.herald_scheduler.client;

import com.yuriolivs.herald_scheduler.config.properties.RoutingProperties;
import com.yuriolivs.notification.shared.domain.notification.dto.NotificationResponseDTO;
import com.yuriolivs.notification.shared.domain.schedule.dto.SchedulePayloadRequestDTO;
import com.yuriolivs.notification.shared.domain.schedule.dto.ScheduledPayloadResponseDTO;
import com.yuriolivs.herald_scheduler.config.properties.SecurityProperties;
import com.yuriolivs.herald_scheduler.domain.notification.dto.NotificationRequestDTO;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Component
@EnableConfigurationProperties({SecurityProperties.class, RoutingProperties.class})
public class NotificationClient {
    private final RoutingProperties routes;
    private final RestTemplate restTemplate;
    private final HttpHeaders headers;

    private static final String INTERNAL_KEY_HEADER = "X-Internal-Key";


    public NotificationClient(
            RestTemplate restTemplate,
            SecurityProperties securityProperties,
            RoutingProperties routingProperties
    ) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(INTERNAL_KEY_HEADER, securityProperties.getInternalKey());

        this.routes = routingProperties;
        this.restTemplate = restTemplate;
        this.headers = headers;
    }

    public NotificationResponseDTO findById(UUID id) {
        String uri = routes.getService() + "/" + id;
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        return restTemplate.exchange(
                uri,
                HttpMethod.GET,
                entity,
                NotificationResponseDTO.class
        ).getBody();

    }

    public NotificationResponseDTO save(NotificationRequestDTO dto) {
        String uri = routes.getService() + "/internal";

        return restTemplate.exchange(
                uri,
                HttpMethod.POST,
                buildEntity(dto),
                NotificationResponseDTO.class
        ).getBody();
    }

    public ScheduledPayloadResponseDTO getNotificationPayload(SchedulePayloadRequestDTO dto) {
        String uri = routes.getService() + "/internal/payload";

        return restTemplate.exchange(
                uri,
                HttpMethod.POST,
                buildEntity(dto),
                ScheduledPayloadResponseDTO.class
        ).getBody();
    }

    private <T> HttpEntity<T> buildEntity(T body) {
        return new HttpEntity<>(body, headers);
    }
}
