package com.yuriolivs.herald.herald_auth.security.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "security")
@Data
public class SecurityProperties {
    private String encryptionKey;
    private String internalKey;
}
