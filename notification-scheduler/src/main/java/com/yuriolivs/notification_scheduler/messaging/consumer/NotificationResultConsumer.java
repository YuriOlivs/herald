package com.yuriolivs.notification_scheduler.messaging.consumer;

import com.yuriolivs.notification.shared.domain.notification.NotificationResult;
import com.yuriolivs.notification.shared.exceptions.http.HttpNotFoundException;
import com.yuriolivs.notification_scheduler.config.RabbitMqConfig;
import com.yuriolivs.notification_scheduler.domain.schedule.entities.ScheduledNotification;
import com.yuriolivs.notification.shared.domain.schedule.enums.ScheduleStatus;
import com.yuriolivs.notification_scheduler.service.SchedulerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;


@Slf4j
@Component
@RequiredArgsConstructor
public class NotificationResultConsumer {
    private final SchedulerService service;

    @RabbitListener(queues = RabbitMqConfig.RESULT_QUEUE)
    public void consume(NotificationResult result) {
        log.info("==================================================");
        log.info("⚙️ STARTED CONSUMER: {} | ScheduleID: {}",
                RabbitMqConfig.RESULT_QUEUE, result.getScheduleId());
        log.info("==================================================");

        try {
            log.info("Received message from {}.", RabbitMqConfig.RESULT_QUEUE);

            ScheduledNotification notification = service.findScheduledNotification(result.getScheduleId());
            notification.setStatus(result.getStatus());

            log.info("Message status: {}", result.getStatus());

            log.info("Updating database...");
            service.save(notification);
        } catch (HttpNotFoundException ex) {
            log.error("Notification was not found in database.", ex);
            throw ex;
        } finally {
            log.info("==================================================");
            log.info("⚙️ ENDED CONSUMER: {} | ScheduleID: {}",
                    RabbitMqConfig.RESULT_QUEUE, result.getScheduleId());
            log.info("==================================================");
        }
    }
}
