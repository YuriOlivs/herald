package com.yuriolivs.notification_service.notification.messaging.producer;

import com.yuriolivs.notification_service.config.RabbitMqConfig;
import com.yuriolivs.notification_service.notification.domain.entities.Notification;
import com.yuriolivs.notification.shared.domain.notification.NotificationMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class NotificationPublisher {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void publish(Notification notification, Map<String, String> payload) {
        String routingKey = notification.getChannel().name().toLowerCase();

        NotificationMessage send = new NotificationMessage(
                notification.getId(),
                notification.getPriority(),
                payload,
                notification.getChannel());

        rabbitTemplate.convertAndSend(
                RabbitMqConfig.EXCHANGE,
                routingKey,
                send,
                msg -> {
                    msg.getMessageProperties()
                            .setPriority(notification.getPriority().value());

                    return msg;
                });
    }
}
