package com.yuriolivs.herald.herald_auth.controller;

import com.yuriolivs.herald.herald_auth.domain.dto.request.*;
import com.yuriolivs.herald.herald_auth.domain.entities.Tenant;
import com.yuriolivs.herald.herald_auth.service.AuthService;
import com.yuriolivs.herald.shared.domain.auth.CreateTenantResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService service;

    @PostMapping("/api-key/generate")
    public ResponseEntity<String> generateApiKey(
            @RequestBody @Valid GenerateApiKeyRequest dto
    ) throws NoSuchAlgorithmException {
        String apiKey = service.generateApiKey(dto);
        return ResponseEntity.ok(apiKey);
    }

    @PostMapping("/api-key/validate")
    public ResponseEntity<Tenant> validateApiKey(
            @RequestBody @Valid ApiKeyValidateRequest dto
    ) {
        Tenant tenant = service.validateApiKey(dto);
        return ResponseEntity.ok(tenant);
    }

    @PostMapping("/api-key/revoke")
    public ResponseEntity<String> revokeApiKey(
            @RequestBody @Valid RevokeApiKeyRequest dto
    ) {
        String result = service.revokeApiKey(dto);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/tenant/create")
    public ResponseEntity<CreateTenantResponse> createTenant(
            @RequestBody @Valid CreateTenantRequest dto
    ) {
        Tenant tenant = service.createTenant(dto);
        CreateTenantResponse response = CreateTenantResponse.builder()
                .id(tenant.getId())
                .name(tenant.getName())
                .build();

        return ResponseEntity.ok(response);
    }

    @PostMapping("/tenant/revoke-keys")
    public ResponseEntity<String> revokeTenantKeys(
            @RequestBody @Valid RevokeTenantKeysRequest dto
    ) {
       String result = service.revokeTenantKeys(dto);
       return ResponseEntity.ok(result);
    }
}
