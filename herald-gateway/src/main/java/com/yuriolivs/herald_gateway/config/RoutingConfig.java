package com.yuriolivs.herald_gateway.config;

import com.yuriolivs.herald_gateway.config.properties.RoutingProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class RoutingConfig {
    private final RoutingProperties routes;

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("scheduler", r -> r
                        .path("/schedule/**")
                        .uri(routes.getScheduler())
                )
                .route("notifications", r -> r
                        .path("/notifications/**")
                        .uri(routes.getService())
                )
                .route("webhooks", r -> r
                        .path("/webhooks/**")
                        .uri(routes.getWebhooks())
                )
                .route("auth-docs", r -> r
                        .path("/v3/api-docs/auth")
                        .filters(f -> f.rewritePath("/v3/api-docs/auth", "/v3/api-docs"))
                        .uri(routes.getAuth())
                )
                .route("service-docs", r -> r
                        .path("/v3/api-docs/service")
                        .filters(f -> f.rewritePath("/v3/api-docs/service", "/v3/api-docs"))
                        .uri(routes.getService())
                )
                .route("scheduler-docs", r -> r
                        .path("/v3/api-docs/scheduler")
                        .filters(f -> f.rewritePath("/v3/api-docs/scheduler", "/v3/api-docs"))
                        .uri(routes.getScheduler())
                )
                .build();
    }
}
