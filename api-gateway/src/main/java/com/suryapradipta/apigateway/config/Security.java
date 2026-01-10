package com.suryapradipta.apigateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class Security {
    private static final String[] WHITELIST = {
            "/actuator/**", // Allow access to actuator endpoints
            "/v3/api-docs/**", // Allow access to OpenAPI documentation
            "/swagger-ui/**", // Allow access to Swagger UI
            "/swagger-resources/**", // Allow access to Swagger resources
            "/swagger-ui.html", // Allow access to Swagger UI HTML page
            "/api-docs/**", // Allow access to API documentation
            "/aggregate/**", // Allow access to aggregate endpoints
    };

    /**
     * Returns a {@link SecurityFilterChain} that requires authentication for all requests, using JWT-based authentication.
     *
     * @param httpSecurity the {@link HttpSecurity} to customize
     * @return the {@link SecurityFilterChain} used for authentication and authorization
     * @throws Exception if a problem occurs while customizing the security filter chain
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(WHITELIST).permitAll() // Allow access to whitelisted endpoints
                        .anyRequest().authenticated())
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(Customizer.withDefaults()))
                .build();
    }
}
