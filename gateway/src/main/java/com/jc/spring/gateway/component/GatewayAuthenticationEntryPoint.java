package com.jc.spring.gateway.component;

import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xiaok
 */
@Component
@Slf4j
public class GatewayAuthenticationEntryPoint implements ServerAuthenticationEntryPoint {

    @Override
    public Mono<Void> commence(ServerWebExchange exchange, AuthenticationException e) {
        log.info("log after1 error message: {}",e.getMessage());
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        response.getHeaders().set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        Map<String, Object> result = new HashMap<>(16);
        result.put("code", HttpStatus.UNAUTHORIZED.value());
        result.put("msg", e.getMessage());
        String res = JSONObject.toJSONString(result);
        DataBuffer buffer =  response.bufferFactory().wrap(res.getBytes(StandardCharsets.UTF_8));
        exchange.getRequest().mutate().header("updated", "yes");
        return response.writeWith(Mono.just(buffer));
    }


}
