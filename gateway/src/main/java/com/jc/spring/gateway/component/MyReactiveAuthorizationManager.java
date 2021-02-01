package com.jc.spring.gateway.component;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Arrays;

/**
 * @author jincheng.zhang
 */
@Component
@Slf4j
public class MyReactiveAuthorizationManager implements ReactiveAuthorizationManager<AuthorizationContext> {
    @Override
    public Mono<AuthorizationDecision> check(Mono<Authentication> mono, AuthorizationContext authorizationContext) {
        ServerHttpRequest request = authorizationContext.getExchange().getRequest();
        String path = request.getURI().getPath();
//        PathMatcher pathMatcher = new AntPathMatcher();

        // 打印下
        return mono
                // 需要认证通过
                .filter(Authentication::isAuthenticated)
                // 答应
                .flatMapIterable(authentication -> {
                    log.info(Arrays.toString(authentication.getAuthorities().toArray()));
                    Jwt jwt = (Jwt) authentication.getPrincipal();
                    log.info(jwt.getClaimAsString("authorities"));
                    return authentication.getAuthorities();
                })
                .map(a ->{
                    log.info(a.getAuthority());
                    return a.getAuthority();
                })
                // 转换成Mono<boolean>  条件是所有都满足
                .all(roleId -> {
                    log.info("roleId {}", roleId);
                    log.info("path {}", path);
                    // 先都通过
                    return true;
                }).map(AuthorizationDecision::new).defaultIfEmpty(new AuthorizationDecision(false));

    }


}
