package com.yuriolivs.notification_service.notification.messaging.producer;

import com.yuriolivs.notification.shared.domain.notification.NotificationResult;
import com.yuriolivs.notification_service.config.RabbitMqConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class NotificationResultPublisher {
    @Autowired
    private final RabbitTemplate rabbitTemplate;

    public void publish(NotificationResult result) {
        if (result.getScheduleId() == null) return;
        log.info("==================================================");
        log.info("✉️ Publishing message to " + RabbitMqConfig.RESULT_QUEUE);
        log.info("RESULT: {}", result.toString());
        log.info("==================================================");

        rabbitTemplate.convertAndSend(
                RabbitMqConfig.EXCHANGE,
                RabbitMqConfig.RESULT_ROUTING_KEY,
                result
        );
    }
}
