package com.yuriolivs.notification_service.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "telegram.config")
@Getter
@Setter
public class TelegramProperties {
    private String apiUrl;
    private String token;
}
