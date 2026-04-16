package com.yuriolivs.herald_service.notification.domain;

import com.yuriolivs.herald_service.notification.domain.dto.NotificationRequestDTO;
import com.yuriolivs.herald_service.notification.domain.entities.Notification;

import java.util.UUID;

public interface NotificationServiceInterface {
    Notification handleNotificationRequest(NotificationRequestDTO dto, UUID tenantId) throws Exception;
    Notification findById(UUID id);
    Notification save(NotificationRequestDTO dto, UUID tenantId);
}
