package com.yuriolivs.herald_service.security;

import com.yuriolivs.herald_service.security.filter.InternalKeyAuthenticationFilter;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableConfigurationProperties(SecurityProperties.class)
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityWebFilterChain(
        HttpSecurity http,
        InternalKeyAuthenticationFilter internalFilter
    ) {
        http
            .csrf(csrf -> csrf.disable())
            .httpBasic(basic -> basic.disable())
            .formLogin(form -> form.disable())
            .authorizeHttpRequests(auth -> auth
                    .requestMatchers(
                            "/swagger-ui/**",
                            "/v3/api-docs/**",
                            "/swagger-ui.html"
                    ).permitAll()
                    .requestMatchers(
                            "/notifications/internal/**",
                            "/internal/**"
                    )
                    .authenticated()
                    .anyRequest().permitAll()
            )
                .addFilterBefore(internalFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
