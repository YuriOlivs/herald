package com.yuriolivs.herald.herald_auth.domain.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Data
@ToString
@Table(
    name = "tenants"
)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Tenant {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;

    private boolean active;

    public Tenant(
            String name,
            boolean active
    ) {
        this.name = name;
        this.active = active;
    }
}
