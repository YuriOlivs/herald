package com.yuriolivs.notification_service.telegram.domain.dto.message;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

public record MessageFromDTO(
    @NotEmpty
    Long id
) {}
