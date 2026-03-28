package com.yuriolivs.notification_service.telegram.domain.dto;

import com.yuriolivs.notification_service.telegram.domain.dto.message.MessageDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TelegramWebhookDTO(
        @NotBlank
        String idempotencyKey,

        @NotNull
        MessageDTO message
) {}
