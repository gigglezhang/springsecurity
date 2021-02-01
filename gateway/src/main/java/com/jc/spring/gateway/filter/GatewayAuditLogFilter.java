package com.jc.spring.gateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;

/**
 * @author jincheng.zhang
 */
// @Component 不要加上
@Slf4j
public class GatewayAuditLogFilter implements WebFilter {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        return ReactiveSecurityContextHolder.getContext()
                .map(securityContext -> {
                    Object principal = securityContext.getAuthentication().getPrincipal();
                    if (principal instanceof Jwt) {
                        Jwt jwt = (Jwt) principal;
                        log.info("user jwt is : {}", jwt.getClaims());
                    }
                    return Mono.empty();
                })
                .then(chain.filter(exchange)
                        .then(Mono.defer(() -> {
                                    List<String> values = exchange.getRequest().getHeaders().get("updated");
                                    if (values == null || values.isEmpty()) {
                                        log.info("after log -----------");
                                    }
                                    return Mono.empty();
                                })
                        )
                );


    }
}
