package com.yuriolivs.notification_service.telegram.domain.dto.message;

import jakarta.validation.constraints.NotBlank;

public record MessageChatDTO (
        @NotBlank
        Long id
) {}
