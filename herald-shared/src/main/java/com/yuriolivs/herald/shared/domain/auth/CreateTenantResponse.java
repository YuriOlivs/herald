package com.yuriolivs.herald.shared.domain.auth;

import lombok.Builder;

import java.util.UUID;

@Builder
public record CreateTenantResponse(
        UUID id,
        String name
) {}
