package com.leaderboard.api_gateway.filter;

import com.leaderboard.api_gateway.config.SecurityConstants;
import com.leaderboard.api_gateway.util.JwtUserInfo;
import com.leaderboard.api_gateway.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
@RequiredArgsConstructor
@Component
public class JwtAuthenticationFilter implements GlobalFilter , Ordered {
    private final JwtUtil jwtUtil;
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path=exchange.getRequest().getURI().getPath();
        boolean isPublic= SecurityConstants.PUBLIC_ENDPOINTS
                .stream()
                .anyMatch(path::startsWith);
        if(isPublic){
            return chain.filter(exchange);
        }
        String authHeader=exchange.getRequest().getHeaders()
                .getFirst(HttpHeaders.AUTHORIZATION);
        if(authHeader==null || !authHeader.startsWith("Bearer")){
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse()
                    .setComplete();
        }
        String token = authHeader.substring(7);
        if(!jwtUtil.validateToken(token)){
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse()
                    .setComplete();
        }
        JwtUserInfo user =
                jwtUtil.extractUserInfo(token);
        ServerHttpRequest modifiedRequest =
                exchange.getRequest().mutate()
                        .header("X-User-Id", user.getUserId().toString())
                        .header("X-User-Email", user.getEmail())
                        .header("X-User-Role", user.getRole())
                        .build();

        return chain.filter(
                exchange.mutate()
                        .request(modifiedRequest)
                        .build()
        );
    }

    @Override
    public int getOrder() {
        return -1;
    }
}
