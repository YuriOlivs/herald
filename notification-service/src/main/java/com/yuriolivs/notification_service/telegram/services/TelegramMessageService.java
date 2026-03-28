package com.yuriolivs.notification_service.telegram.services;

import com.yuriolivs.notification.shared.exceptions.http.HttpBadRequestException;
import com.yuriolivs.notification_service.telegram.domain.dto.TelegramMessageDTO;
import com.yuriolivs.notification_service.config.properties.TelegramProperties;
import com.yuriolivs.notification_service.telegram.TelegramUser;
import com.yuriolivs.notification_service.telegram.domain.enums.TelegramMessages;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
@AllArgsConstructor
public class TelegramMessageService {
    private final TelegramUserService userService;
    private final RestTemplate restTemplate;
    private final TelegramProperties config;

    public void sendMessage(TelegramMessageDTO dto)  {
        TelegramUser telegramUser = userService.findByUserId(dto.userId());
        if (!telegramUser.isActive()) throw new HttpBadRequestException("User does not have notifications activated.");

        String url = String.format(
                "%s/bot%s/sendMessage",
                config.getApiUrl(),
                config.getToken()
        );

        Map<String, Object> body = Map.of(
                "chat_id", telegramUser.getChatId(),
                "text", dto.message()
        );

        restTemplate.postForEntity(url, body, Void.class);
    }

    public void sendStandardizedMessages(Long chatId, TelegramMessages message) {
        String url = String.format(
                "%s/bot%s/sendMessage",
                config.getApiUrl(),
                config.getToken()
        );

        Map<String, Object> body = Map.of(
                "chat_id", chatId,
                "text", message.value()
        );

        restTemplate.postForEntity(url, body, Void.class);
    }
}
