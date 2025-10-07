package com.microsv.task_service.config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

import javax.crypto.spec.SecretKeySpec;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity // Bật annotation @PreAuthorize nếu bạn muốn dùng sau này
public class SecurityConfig {

    @Value("${jwt.secret}")
    private String secretKey;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // PUBLIC APIS (nếu có) - ví dụ: health check, internal endpoints
                        .requestMatchers("/internal/**").permitAll()
//                        .requestMatchers("/actuator/health").permitAll()

                        // TASK APIS - TẤT CẢ CẦN AUTHENTICATION
                        .requestMatchers(HttpMethod.GET, "/api/tasks").authenticated()     // Get all tasks
                        .requestMatchers(HttpMethod.GET, "/api/tasks/**").authenticated()  // Get task by id
                        .requestMatchers(HttpMethod.POST, "/api/tasks").authenticated()    // Create task
                        .requestMatchers(HttpMethod.PUT, "/api/tasks/**").authenticated()  // Update task
                        .requestMatchers(HttpMethod.DELETE, "/api/tasks/**").authenticated() // Delete task

                        // HOẶC đơn giản hơn - tất cả task APIs đều cần auth:
                        // .requestMatchers("/api/tasks/**").authenticated()
// Có thể thêm phân quyền chi tiết sau:
                        .requestMatchers(HttpMethod.GET, "/api/tasks/admin").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/tasks/**").hasAnyRole("ADMIN")
                        .anyRequest().authenticated() // Mặc định tất cả APIs khác cần auth
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt
                                .decoder(jwtDecoder())
                                .jwtAuthenticationConverter(jwtAuthenticationConverter())
                        )
                );

        return http.build();
    }

    // Bean này sẽ đọc claim "scope" và chuyển nó thành các quyền (authorities)
    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        jwtGrantedAuthoritiesConverter.setAuthoritiesClaimName("scope"); // Đọc từ claim "scope"
        jwtGrantedAuthoritiesConverter.setAuthorityPrefix(""); // Bỏ tiền tố "SCOPE_"

        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
        return jwtAuthenticationConverter;
    }

    // Bean này chịu trách nhiệm giải mã và xác thực chữ ký của JWT
    @Bean
    public JwtDecoder jwtDecoder() {
        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(), "HS384");
        return NimbusJwtDecoder
                .withSecretKey(secretKeySpec)
                .macAlgorithm(MacAlgorithm.HS384)
                .build();
    }
}