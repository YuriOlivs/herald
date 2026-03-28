package com.yuriolivs.notification_service.notification;

import com.yuriolivs.notification_service.notification.domain.entities.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface NotificationRepository extends JpaRepository<Notification, UUID> {
    Optional<Notification> findByIdempotencyKey(String idempotencyKey);
    List<Notification> findAllById(Iterator<UUID> ids);
}
