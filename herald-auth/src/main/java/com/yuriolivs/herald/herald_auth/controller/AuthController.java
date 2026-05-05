package com.yuriolivs.herald.herald_auth.controller;

import com.yuriolivs.herald.herald_auth.domain.dto.request.*;
import com.yuriolivs.herald.herald_auth.domain.entities.Tenant;
import com.yuriolivs.herald.herald_auth.service.AuthService;
import com.yuriolivs.herald.shared.domain.auth.CreateTenantResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(
        name = "Authentication Service",
        description = "Endpoints for API Key and Tenant management"
)
public class AuthController {
    private final AuthService service;

    @PostMapping("/api-key/generate")
    @Operation(
            summary = "Generate API Key",
            description = "Generates a new API Key a tenant"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "API Key generated successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized — invalid internal key"),
            @ApiResponse(responseCode = "404", description = "Tenant not found")
    })
    public ResponseEntity<String> generateApiKey(
            @RequestBody @Valid GenerateApiKeyRequest dto
    ) throws NoSuchAlgorithmException {
        String apiKey = service.generateApiKey(dto);
        return ResponseEntity.ok(apiKey);
    }

    @PostMapping("/api-key/validate")
    @Operation(
            summary = "Validate API Key",
            description = "Validates an API Key and returns the Tenant if valid"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "API Key is valid"),
            @ApiResponse(responseCode = "401", description = "Invalid or inactive API Key")
    })
    public ResponseEntity<Tenant> validateApiKey(
            @RequestBody @Valid ApiKeyValidateRequest dto
    ) {
        Tenant tenant = service.validateApiKey(dto);
        return ResponseEntity.ok(tenant);
    }

    @PostMapping("/api-key/revoke")
    @Operation(
            summary = "Revoke API Key",
            description = "Deactivates a specific API Key"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "API Key revoked successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized — invalid internal key"),
            @ApiResponse(responseCode = "404", description = "API Key not found")
    })
    public ResponseEntity<String> revokeApiKey(
            @RequestBody @Valid RevokeApiKeyRequest dto
    ) {
        String result = service.revokeApiKey(dto);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/tenant/create")
    @Operation(
            summary = "Create Tenant",
            description = "Creates a Tenant in the system"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Tenant created successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized — invalid internal key")
    })
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
    @Operation(
            summary = "Revoke all Tenant keys",
            description = "Deactivates all API Keys associated with a tenant"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "All keys revoked successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized — invalid internal key"),
            @ApiResponse(responseCode = "404", description = "Tenant not found or no active keys found")
    })
    public ResponseEntity<String> revokeTenantKeys(
            @RequestBody @Valid RevokeTenantKeysRequest dto
    ) {
       String result = service.revokeTenantKeys(dto);
       return ResponseEntity.ok(result);
    }
}
