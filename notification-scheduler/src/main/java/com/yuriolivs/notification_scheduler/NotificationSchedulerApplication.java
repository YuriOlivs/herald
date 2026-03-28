package com.yuriolivs.notification_scheduler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class NotificationSchedulerApplication {

	public static void main(String[] args) {
		SpringApplication.run(NotificationSchedulerApplication.class, args);
	}

}
