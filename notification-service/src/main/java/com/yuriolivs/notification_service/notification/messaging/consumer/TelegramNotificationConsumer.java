package com.yuriolivs.notification_service.notification.messaging.consumer;

import com.yuriolivs.notification.shared.domain.notification.NotificationMessage;
import com.yuriolivs.notification.shared.domain.notification.NotificationResult;
import com.yuriolivs.notification.shared.domain.schedule.enums.ScheduleStatus;
import com.yuriolivs.notification_service.config.RabbitMqConfig;
import com.yuriolivs.notification_service.notification.NotificationRepository;
import com.yuriolivs.notification_service.notification.domain.entities.Notification;
import com.yuriolivs.notification.shared.domain.notification.enums.NotificationStatus;
import com.yuriolivs.notification.shared.domain.notification.enums.NotificationType;
import com.yuriolivs.notification_service.notification.messaging.producer.NotificationResultPublisher;
import com.yuriolivs.notification_service.telegram.domain.dto.TelegramMessageDTO;
import com.yuriolivs.notification_service.telegram.services.TelegramMessageService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

@Slf4j
@Component
@AllArgsConstructor
public class TelegramNotificationConsumer {
    private final NotificationRepository repo;
    private final TelegramMessageService messageService;
    private final ObjectMapper objectMapper;
    private final NotificationResultPublisher resultPublisher;

    @RabbitListener(queues = RabbitMqConfig.TELEGRAM_QUEUE)
    public void consume(NotificationMessage received) {
        Notification notification = repo.findById(received.getId()).orElseThrow();
        NotificationResult result = NotificationResult.from(received);

        log.info("==================================================");
        log.info("⚙️ STARTED CONSUMER: {} | Message: {}",
                RabbitMqConfig.MAIL_QUEUE, received.getId());
        log.info("==================================================");

        try {
            NotificationType type = notification.getType();

            switch (type) {
                case TELEGRAM_MESSAGE -> {
                    TelegramMessageDTO dto = objectMapper.convertValue(
                            received.getPayload(),
                            TelegramMessageDTO.class
                    );
                    messageService.sendMessage(dto);
                }
            }

            notification.setStatus(NotificationStatus.SENT);

            result.setStatus(ScheduleStatus.EXECUTED);
            result.setMessage("Message sent with success.");
        } catch (Exception ex) {
            notification.setStatus(NotificationStatus.FAILED);

            result.setStatus(ScheduleStatus.FAILED);
            result.setMessage("There was an error sending the message.");

            throw ex;
        }

        repo.save(notification);
        resultPublisher.publish(result);

        log.info("==================================================");
        log.info("⚙️ ENDED CONSUMER: {} | Message: {}",
                RabbitMqConfig.MAIL_QUEUE, received.getId());
        log.info("==================================================");
    }
}
