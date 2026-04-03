package com.yuriolivs.herald_gateway.client;

import com.yuriolivs.herald.shared.domain.auth.ApiKeyValidate;
import com.yuriolivs.herald.shared.domain.auth.CreateTenantResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class AuthClient {
    private final RestTemplate restTemplate;

    public CreateTenantResponse validateKey(String key) {
        ApiKeyValidate keyValidate = ApiKeyValidate.builder()
                .apiKey(key)
                .build();

        return restTemplate.postForObject(
                "http://localhost:8084/auth/api-key/validate",
                keyValidate,
                CreateTenantResponse.class
        );
    }
}
