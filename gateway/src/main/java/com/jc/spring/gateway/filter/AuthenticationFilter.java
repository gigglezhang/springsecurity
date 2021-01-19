package com.jc.spring.gateway.filter;

import com.jc.spring.gateway.bean.TokenInfo;
import com.jc.spring.gateway.feignapi.Oauth2Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author jincheng.zhang
 */
//@Component
@Slf4j
public class AuthenticationFilter implements GlobalFilter, Ordered {

    @Autowired
    Oauth2Api oauth2Api;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("Authentication start");
        String uri  = exchange.getRequest().getURI().getPath();
        if(StringUtils.startsWith(uri, "/token")){
            return chain.filter(exchange);
        }
        String authorization = exchange.getRequest().getHeaders().getFirst("Authorization");
        // 没有带认证的不做身份认证。ps: 流程是认证都会往后走
        if(StringUtils.isBlank(authorization)){
            return chain.filter(exchange);
        }
        // 如果不是bearer 类型的也不做认证 ps: 流程是认证都会往后走
        if(!StringUtils.startsWithIgnoreCase(authorization, "Bearer ")){
            return chain.filter(exchange);
        }
        try {
            TokenInfo tokenInfo = getTokenInfo(authorization);
//            exchange.getAttributes().put("tokenInfo",tokenInfo);
            ServerHttpRequest serverHttpRequest = exchange.getRequest().mutate().header("username", tokenInfo.getUser_name()).build();
            return chain.filter(exchange.mutate().request(serverHttpRequest).build());
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }

        return chain.filter(exchange);
    }

    private TokenInfo getTokenInfo(String authorization) {
        String token = StringUtils.substringAfter(authorization, "Bearer ");
        MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
        multiValueMap.add("token", token);
//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//        httpHeaders.set("aaa","11");
//        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(multiValueMap,httpHeaders);
        return oauth2Api.getTokenInfo(multiValueMap);
    }

    @Override
    public int getOrder() {
        return 1;
    }
}
