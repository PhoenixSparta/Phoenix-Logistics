package com.phoenix.logistics.support.authentication.security;

import com.phoenix.logistics.support.authentication.security.filter.CustomAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
@ComponentScan("com.phoenix.logistics.support.authentication")
public class SecurityConfig {

    private final CustomAuthenticationFilter customAuthenticationFilter;



    public SecurityConfig(CustomAuthenticationFilter customAuthenticationFilter, RoleHierarchy roleHierarchy) {
        this.customAuthenticationFilter = customAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable) // CSRF 비활성화
            .authorizeHttpRequests(authz -> authz.anyRequest().authenticated() // 모든 요청은
                                                                               // 인증이 필요
            )
            .addFilterBefore(customAuthenticationFilter, UsernamePasswordAuthenticationFilter.class); // 커스텀
                                                                                                      // 필터
                                                                                                      // 추가

        return http.build();
    }

}
