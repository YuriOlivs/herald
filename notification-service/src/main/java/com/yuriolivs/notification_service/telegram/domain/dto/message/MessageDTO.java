package com.yuriolivs.notification_service.telegram.domain.dto.message;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record MessageDTO (
    @NotNull
    MessageChatDTO chat,

    @NotNull
    MessageFromDTO from,

    @NotBlank
    String text
) {}