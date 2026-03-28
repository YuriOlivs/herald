package com.yuriolivs.notification_scheduler.service;

import com.yuriolivs.notification.shared.domain.notification.NotificationMessage;
import com.yuriolivs.notification_scheduler.messaging.producer.NotificationPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class SchedulerJob {
    @Autowired private final SchedulerService service;
    @Autowired private final NotificationPublisher publisher;

    @Scheduled(fixedDelay = 10000)
    public void execute() {
        long startTime = System.currentTimeMillis();

        log.info("==================================================");
        log.info("🚀 SCHEDULER JOB STARTED" );
        log.info("==================================================");

        try {
            LocalDate today = LocalDate.now();
            LocalDateTime startOfDay = today.atStartOfDay();
            LocalDateTime now = LocalDateTime.now();

            List<NotificationMessage> notifications = service
                    .findNotificationsToBeProcessed(startOfDay, now);

            log.info("Found {} notifications to process.", notifications.size());

            if (!notifications.isEmpty()) {
                for (NotificationMessage send : notifications) {
                    try {
                        publisher.publish(send);
                        log.debug("Message {} published with success.", send.getId());
                    }
                    catch (Exception ex) {
                        log.error("Error publishing message {} to {} queue.", send.getId(), send.getChannel(), ex);
                    }
                }
            }

            if (notifications.isEmpty()) {
                log.info("No messages found to be processed.");
            }
        } catch (Exception ex) {
            log.error("Unexpected error during Scheduler job execution.");
            throw ex;
        } finally {
            long duration = System.currentTimeMillis() - startTime;
            log.info("==================================================");
            log.info("✅ SCHEDULER JOB FINISHED | Duration = {} ms", duration);
            log.info("==================================================");
        }
    }
}
