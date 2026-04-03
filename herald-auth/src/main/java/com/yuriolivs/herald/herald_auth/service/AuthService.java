package com.yuriolivs.herald.herald_auth.service;

import com.yuriolivs.herald.herald_auth.domain.dto.request.*;
import com.yuriolivs.herald.herald_auth.domain.entities.ApiKey;
import com.yuriolivs.herald.herald_auth.domain.entities.Tenant;
import com.yuriolivs.herald.herald_auth.repository.ApiKeyRepository;
import com.yuriolivs.herald.herald_auth.repository.TenantRepository;
import com.yuriolivs.herald.herald_auth.security.ApiKeyGenerator;
import com.yuriolivs.herald.shared.exceptions.http.HttpNotFoundException;
import com.yuriolivs.herald.shared.exceptions.http.HttpUnauthorizedException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService implements AuthServiceInterface{
    @Autowired
    private TenantRepository tenantRepo;

    @Autowired
    private ApiKeyRepository keyRepo;

    @Autowired
    private PasswordEncoder encoder;

    @Override
    public Tenant validateApiKey(ApiKeyValidateRequest dto) {
        String[] keyParts = dto.apiKey().split("\\.", 2);
        String prefix = keyParts[0];
        String secret = keyParts[1];

        Optional<ApiKey> keyFound = keyRepo.findByPrefix(prefix);
        if (keyFound.isEmpty() || !keyFound.get().isActive())
            throw new HttpUnauthorizedException("Invalid API Key");

        if(!encoder.matches(secret, keyFound.get().getSecretHash()))
            throw new HttpUnauthorizedException("Invalid API Key");

        Optional<Tenant> tenantFound = tenantRepo.findById(keyFound.get().getTenantId());
        if (tenantFound.isEmpty() || !tenantFound.get().isActive())
            throw new HttpUnauthorizedException("Invalid API Key");

        return tenantFound.get();
    }

    @Override
    public Tenant createTenant(CreateTenantRequest dto) {
        String tenantName = dto.name();

        Tenant tenantToBeCreated = Tenant.builder()
                .name(tenantName)
                .active(true)
                .build();

        return tenantRepo.save(tenantToBeCreated);
    }

    @Override
    public String revokeApiKey(RevokeApiKeyRequest dto) {
        String[] keyParts = dto.apiKey().split("\\.", 2);
        String prefix = keyParts[0];
        String secret = keyParts[1];

        Optional<ApiKey> keyFound = keyRepo.findByPrefix(prefix);
        if (keyFound.isEmpty() || !keyFound.get().isActive())
            throw new HttpUnauthorizedException("Invalid API Key");

        if(!encoder.matches(secret, keyFound.get().getSecretHash()))
            throw new HttpUnauthorizedException("Invalid API Key");

        ApiKey keyToBeRevoked = keyFound.get();
        keyToBeRevoked.setActive(false);

        keyRepo.save(keyToBeRevoked);

        return "Revoked API Key successfully";
    }

    @Override
    public String revokeTenantKeys(RevokeTenantKeysRequest dto) {
        Optional<Tenant> tenantFound = tenantRepo.findById(dto.tenantId());
        if (tenantFound.isEmpty())
            throw new HttpNotFoundException("Tenant not found");

        List<ApiKey> keys = keyRepo.findAllByTenantId(tenantFound.get().getId());
        if (keys.isEmpty())
            throw new HttpNotFoundException("No keys found");

        keys.forEach(key -> key.setActive(false));

        keyRepo.saveAll(keys);

        return "All tenant keys were deactivated";
    }

    @Override
    public String generateApiKey(GenerateApiKeyRequest dto) throws NoSuchAlgorithmException {
        String prefix = ApiKeyGenerator.generatePrefix(dto.tenantId());
        String secret = ApiKeyGenerator.generateSecret();

        String hashedSecret = encoder.encode(secret);

        ApiKey key = ApiKey.builder()
                .prefix(prefix)
                .secretHash(hashedSecret)
                .tenantId(dto.tenantId())
                .active(true)
                .build();

        keyRepo.save(key);


        return String.join(".", prefix, secret);
    }
}
