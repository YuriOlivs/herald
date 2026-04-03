package com.yuriolivs.herald.herald_auth.repository;

import com.yuriolivs.herald.herald_auth.domain.entities.ApiKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ApiKeyRepository extends JpaRepository<ApiKey, UUID> {
    Optional<ApiKey> findByPrefix(String prefix);
    List<ApiKey> findAllByTenantId(UUID tenantId);
}
