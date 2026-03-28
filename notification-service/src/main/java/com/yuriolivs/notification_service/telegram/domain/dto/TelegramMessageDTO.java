package com.yuriolivs.notification_service.telegram.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

public record TelegramMessageDTO(
    @NotBlank
    String idempotencyKey,

    @NotBlank
    Long userId,

    @NotBlank
    @Size(max = 255, min = 3)
    String message
) {}
