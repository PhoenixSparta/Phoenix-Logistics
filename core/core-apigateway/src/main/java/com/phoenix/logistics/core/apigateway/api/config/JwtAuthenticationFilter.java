package com.phoenix.logistics.core.apigateway.api.config;

import com.phoenix.logistics.db.core.redis.service.RedisService;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@Order(0) // 필터 순서를 지정
public class JwtAuthenticationFilter implements GlobalFilter {

    private final WebClient.Builder webClientBuilder;

    private final RedisService redisService;

    @Autowired
    public JwtAuthenticationFilter(WebClient.Builder webClientBuilder, RedisService redisService) {
        this.webClientBuilder = webClientBuilder;
        this.redisService = redisService;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getURI().getPath();

        // /auth 경로는 필터를 적용하지 않음
        if (path.startsWith("/api/v1/auth")) {
            return chain.filter(exchange);
        }

        // JWT 토큰 추출
        String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            // 토큰이 없거나 잘못된 경우 401 응답
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        String token = authHeader.substring(7); // "Bearer " 제거

        // 1. JWT Payload에서 필요한 정보를 추출
        String payload = extractPayload(token); // Payload 추출 메서드 (Base64 decode)

        // 2. Redis에서 인증 정보 조회
        String redisKey = "auth:" + payload;
        Map<String, Object> cachedClaims = (Map<String, Object>) redisService.getValue(redisKey);

        if (cachedClaims != null) {
            // Redis에 정보가 있으면 헤더에 추가하고 라우팅
            exchange.getRequest()
                .mutate()
                .header("X-User-Id", cachedClaims.get("sub").toString())
                .header("X-Role", cachedClaims.get("role").toString());
            return chain.filter(exchange);
        }

        // 3. Redis에 정보가 없으면 Auth 서버로 JWT 검증 요청
        return webClientBuilder.build()
            .post()
            .uri("http://localhost:8081/api/v1/auth/validate-token")
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
            .retrieve()
            .bodyToMono(Map.class)
            .flatMap(responseBody -> {
                // 응답으로부터 유저 정보 추출 후 Redis에 저장
                redisService.setValueWithExpiry(redisKey, responseBody, 1, TimeUnit.HOURS); // 1시간
                                                                                            // TTL
                                                                                            // 설정

                // 유저 정보를 헤더에 추가
                exchange.getRequest()
                    .mutate()
                    .header("X-User-Id", responseBody.get("sub").toString())
                    .header("X-Role", responseBody.get("role").toString());

                return chain.filter(exchange);
            })
            .onErrorResume(e -> {
                // 검증 실패 시 401 반환
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            });
    }

    // JWT의 Payload 추출 메서드 (Base64 디코딩)
    private String extractPayload(String token) {
        String[] parts = token.split("\\.");
        if (parts.length < 2) {
            throw new IllegalArgumentException("Invalid JWT token format.");
        }
        return new String(java.util.Base64.getDecoder().decode(parts[1]));
    }

}
