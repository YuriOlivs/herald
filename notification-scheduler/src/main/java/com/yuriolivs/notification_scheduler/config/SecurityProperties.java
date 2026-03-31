package com.yuriolivs.notification_scheduler.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "security")
@RequiredArgsConstructor
@Getter
public class SecurityProperties {
    private String internalKey;
}
