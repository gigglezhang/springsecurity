package com.jc.spring.gateway.component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.google.common.net.HttpHeaders;
import com.nimbusds.jose.util.JSONObjectUtils;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
import net.minidev.json.JSONUtil;
import net.minidev.json.JSONValue;
import net.minidev.json.parser.JSONParser;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.MimeType;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xiaok
 */
@Slf4j
@Component
public class Oauth2AccessDeniedHandler implements ServerAccessDeniedHandler {
    @Override
    public Mono<Void> handle(ServerWebExchange exchange, AccessDeniedException denied) {
        log.debug("log after2 error message: {} cause: {}",denied.getMessage(),denied.getCause());
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.FORBIDDEN);
        response.getHeaders().set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        Map<String, Object> result = new HashMap<>(16);
        result.put("code", HttpStatus.FORBIDDEN.value());
        result.put("msg", denied.getMessage());
        String res = JSONObject.toJSONString(result);
        DataBuffer buffer =  response.bufferFactory().wrap(res.getBytes(StandardCharsets.UTF_8));
        exchange.getRequest().mutate().header("updated", "yes");
        return exchange.getResponse().writeWith(Mono.just(buffer));
    }
}
