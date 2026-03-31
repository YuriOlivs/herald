package com.yuriolivs.notification_gateway.filter;

import com.yuriolivs.notification_gateway.properties.NotificationGatewayProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@EnableConfigurationProperties(NotificationGatewayProperties.class)
@RequiredArgsConstructor
@Slf4j
public class    ApiKeyAuthenticationFilter implements GlobalFilter {
    private final NotificationGatewayProperties gatewayProperties;

    @Override
    public Mono<Void> filter(
        ServerWebExchange exchange,
        GatewayFilterChain chain
    ) {
        String apiKey = gatewayProperties.getApiKey();
        String receivedKey = exchange.getRequest().getHeaders().getFirst("X-API-Key");

        if (!apiKey.equals(receivedKey)) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        return chain.filter(exchange);
    }
}
