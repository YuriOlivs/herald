package com.yuriolivs.notification_gateway.filter;

import com.yuriolivs.notification_gateway.properties.GatewayProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@EnableConfigurationProperties(GatewayProperties.class)
@RequiredArgsConstructor
public class ApiKeyAuthenticationFilter implements GlobalFilter {
    private final GatewayProperties gatewayProperties;

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
