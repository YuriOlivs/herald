package com.yuriolivs.herald.herald_auth.domain.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@ToString
@Table(
        name = "api_keys"
)
@Builder
@AllArgsConstructor
public class ApiKey {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private UUID tenantId;

    private String prefix;

    private String secretHash;

    private boolean active;

    public ApiKey(
        UUID tenantId,
        String prefix,
        String secretHash,
        boolean active
    ) {
        this.tenantId = tenantId;
        this.prefix = prefix;
        this.secretHash = secretHash;
        this.active = active;
    }
}
