    package com.yuriolivs.herald_gateway.filter;

    import com.yuriolivs.herald_gateway.client.AuthClient;
    import com.yuriolivs.herald_gateway.config.properties.NotificationGatewayProperties;
    import lombok.RequiredArgsConstructor;
    import lombok.extern.slf4j.Slf4j;
    import org.springframework.boot.context.properties.EnableConfigurationProperties;
    import org.springframework.cloud.gateway.filter.GatewayFilterChain;
    import org.springframework.cloud.gateway.filter.GlobalFilter;
    import org.springframework.http.HttpStatus;
    import org.springframework.stereotype.Component;
    import org.springframework.web.server.ServerWebExchange;
    import reactor.core.publisher.Mono;

    import java.util.List;
    import java.util.UUID;

    @Component
    @EnableConfigurationProperties(NotificationGatewayProperties.class)
    @RequiredArgsConstructor
    @Slf4j
    public class    ApiKeyAuthenticationFilter implements GlobalFilter {
        private final NotificationGatewayProperties gatewayProperties;
        private final AuthClient client;
        private static final List<String> EXCLUDED_PATHS = List.of(
                "/v3/api-docs",
                "/swagger-ui",
                "webjars"
        );

        @Override
        public Mono<Void> filter(
            ServerWebExchange exchange,
            GatewayFilterChain chain
        ) {
            String path = exchange.getRequest().getPath().toString();

            String receivedKey = exchange.getRequest().getHeaders().getFirst("X-API-Key");
            UUID tenantId = client.validateKey(receivedKey).id();

            boolean isExcluded = EXCLUDED_PATHS.stream().anyMatch(path::startsWith);
            if(isExcluded) {
                return chain.filter(exchange);
            }

            if (tenantId == null) {
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }

            ServerWebExchange mutatedExchange = exchange.mutate()
                    .request(r -> r.header("X-Tenant-Id", tenantId.toString()))
                    .build();

            return chain.filter(mutatedExchange);
        }
    }
