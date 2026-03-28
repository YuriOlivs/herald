package com.yuriolivs.notification_scheduler.messaging.producer;

import com.yuriolivs.notification.shared.domain.notification.NotificationMessage;
import com.yuriolivs.notification_scheduler.config.RabbitMqConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class NotificationPublisher {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void publish(NotificationMessage send) {
        String routingKey = send.getChannel().name().toLowerCase();

        rabbitTemplate.convertAndSend(
            RabbitMqConfig.EXCHANGE,
            routingKey,
            send,
            msg -> {
            msg.getMessageProperties()
                    .setPriority(send.getPriority().value());

            return msg;
            }
        );
    }
}
