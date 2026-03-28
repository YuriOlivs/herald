package com.yuriolivs.notification_service.notification.messaging.consumer;

import com.yuriolivs.notification.shared.domain.notification.NotificationResult;
import com.yuriolivs.notification.shared.domain.schedule.enums.ScheduleStatus;
import com.yuriolivs.notification_service.config.RabbitMqConfig;
import com.yuriolivs.notification_service.mail.MailService;
import com.yuriolivs.notification_service.mail.domain.dto.MailDTO;
import com.yuriolivs.notification_service.mail.domain.dto.OrderTrackingMailDTO;
import com.yuriolivs.notification_service.notification.NotificationRepository;
import com.yuriolivs.notification_service.notification.domain.entities.Notification;
import com.yuriolivs.notification.shared.domain.notification.enums.NotificationStatus;
import com.yuriolivs.notification.shared.domain.notification.enums.NotificationType;
import com.yuriolivs.notification.shared.domain.notification.NotificationMessage;
import com.yuriolivs.notification_service.notification.messaging.producer.NotificationResultPublisher;
import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class MailNotificationConsumer {
    private final NotificationRepository repo;
    private final MailService mailService;
    private final NotificationResultPublisher resultPublisher;
    private final ObjectMapper objectMapper;

    @RabbitListener(queues = RabbitMqConfig.MAIL_QUEUE)
    public void consume(NotificationMessage received) throws MessagingException, IOException {
        Notification notification = repo.findById(received.getId()).orElseThrow();
        NotificationResult result = NotificationResult.from(received);

        log.info("==================================================");
        log.info("⚙️ STARTED CONSUMER: {} | Message: {}",
                RabbitMqConfig.MAIL_QUEUE, received.getId());
        log.info("==================================================");

        try {
            NotificationType type = notification.getType();
            switch (type) {
                case SIMPLE_EMAIL -> {
                    MailDTO dto = objectMapper.convertValue(
                            received.getPayload(),
                            MailDTO.class
                    );
                    mailService.sendEmail(dto);
                }

                case EMAIL_ORDER_TRACKING -> {
                    OrderTrackingMailDTO dto = objectMapper.convertValue(
                            received.getPayload(),
                            OrderTrackingMailDTO.class
                    );
                    mailService.sendOrderTrackingMail(dto);
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