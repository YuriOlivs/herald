package com.yuriolivs.herald.herald_auth.repository;

import com.yuriolivs.herald.herald_auth.domain.entities.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TenantRepository extends JpaRepository<Tenant, UUID> {

}
