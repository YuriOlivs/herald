package com.yuriolivs.herald.herald_auth.domain.dto.request;

import jakarta.validation.constraints.NotBlank;

public record RevokeApiKeyRequest(
        @NotBlank
        String apiKey
) {}
