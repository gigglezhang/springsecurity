package com.jc.spring.gateway.component;

import com.google.common.net.HttpHeaders;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xiaok
 */
@Slf4j
@Component
public class Oauth2AccessDeniedHandler implements ServerAccessDeniedHandler {
    /**
     * 认证成功但是授权被拒接才会执行，如果认证失败不走这个
     * @param exchange exchange
     * @param denied denied
     * @return Mono<Void>
     */

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, AccessDeniedException denied) {
        log.info("ServerAccessDeniedHandler error message: {} cause: {}",denied.getMessage(),denied.getCause());
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.FORBIDDEN);
        response.getHeaders().set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        Map<String, Object> result = new HashMap<>(16);
        result.put("code", HttpStatus.FORBIDDEN.value());
        result.put("msgServerAccessDeniedHandler", denied.getMessage());
        DataBuffer buffer =  response.bufferFactory().wrap(JSONObject.toJSONString(result).getBytes(StandardCharsets.UTF_8));
        exchange.getRequest().mutate().header("updated", "yes");
        return exchange.getResponse().writeWith(Mono.just(buffer));
    }
}
