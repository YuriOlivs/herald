package com.yuriolivs.notification_gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RoutingConfig {
    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("scheduler", r -> r
                        .path("/schedule/**")
                        .uri("http://localhost:8082/schedule")
                )
                .route("notifications", r -> r
                        .path("/notifications/**")
                        .uri("http://localhost:8083/notifications")
                )
                .route("webhooks", r -> r
                        .path("/webhooks/**")
                        .uri("http://localhost:8083/webhooks")
                )
                .build();
    }
}
