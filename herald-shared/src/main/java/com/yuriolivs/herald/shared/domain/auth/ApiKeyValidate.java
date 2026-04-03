package com.yuriolivs.herald.shared.domain.auth;

import lombok.Builder;

@Builder
public record ApiKeyValidate(
        String apiKey
) { }
