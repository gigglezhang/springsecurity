package com.jc.spring.gateway.component;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.security.auth.login.CredentialNotFoundException;
import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;

/**
 * @author jincheng.zhang
 */
@Component
@Slf4j
public class MyReactiveAuthorizationManager implements ReactiveAuthorizationManager<AuthorizationContext> {
    @Override
    public Mono<AuthorizationDecision> check(Mono<Authentication> mono, AuthorizationContext authorizationContext) {
        return mono
            .filter(authentication -> authentication.isAuthenticated() && authentication.getPrincipal() instanceof Jwt)
            .flatMap(authentication -> Mono.just(new AuthorizationDecision(false)))
            .switchIfEmpty( Mono.defer( () -> {
                log.debug("Authentication is empty");
                return Mono.just(new AuthorizationDecision(false));
            }));
    }

}
