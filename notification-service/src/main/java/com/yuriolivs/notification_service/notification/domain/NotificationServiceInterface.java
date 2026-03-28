package com.yuriolivs.notification_service.notification.domain;

import com.yuriolivs.notification_service.notification.domain.dto.NotificationRequestDTO;
import com.yuriolivs.notification_service.notification.domain.entities.Notification;

import java.io.IOException;
import java.util.UUID;

public interface NotificationServiceInterface {
    Notification handleNotificationRequest(NotificationRequestDTO dto) throws Exception;
    Notification findById(UUID id);
    Notification save(NotificationRequestDTO dto);
}
