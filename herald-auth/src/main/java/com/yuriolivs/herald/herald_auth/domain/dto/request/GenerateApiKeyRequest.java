package com.yuriolivs.herald.herald_auth.domain.dto.request;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record GenerateApiKeyRequest(
        @NotNull
        UUID tenantId
) {
}
