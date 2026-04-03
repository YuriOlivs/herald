package com.yuriolivs.herald.herald_auth.security;

import com.yuriolivs.herald.herald_auth.security.utils.HashUtils;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class ApiKeyGenerator {
    private static final String CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final SecureRandom random = new SecureRandom();
    private static final Integer SECRET_LENGTH = 32;

    public static String generatePrefix(UUID tenantId) throws NoSuchAlgorithmException {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

        String timestamp = LocalDateTime.now().format(formatter);
        Integer randomInt = ThreadLocalRandom.current().nextInt(100, 1000);

        String stringToHash = String.join(":",
                tenantId.toString(),
                timestamp,
                String.valueOf(randomInt)
        );

        String hashed = HashUtils.hash(stringToHash);
        String tenantChars = tenantId.toString().substring(0, 4);

        return String.join("_", tenantChars, hashed.substring(0, 8));
    }

    public static String generateSecret() {
        StringBuilder sb = new StringBuilder(SECRET_LENGTH);

        for (int i = 0; i < SECRET_LENGTH; i++) {
            sb.append(CHARS.charAt(random.nextInt(CHARS.length())));
        }

        return sb.toString();
    }
}
