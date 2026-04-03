package com.yuriolivs.herald.herald_auth.service;

import com.yuriolivs.herald.herald_auth.domain.dto.request.*;
import com.yuriolivs.herald.herald_auth.domain.entities.Tenant;

import java.security.NoSuchAlgorithmException;

public interface AuthServiceInterface {
    Tenant validateApiKey(ApiKeyValidateRequest dto);
    Tenant createTenant(CreateTenantRequest dto);
    String generateApiKey(GenerateApiKeyRequest dto) throws NoSuchAlgorithmException;
    String revokeApiKey(RevokeApiKeyRequest dto);
    String revokeTenantKeys(RevokeTenantKeysRequest dto);
}
