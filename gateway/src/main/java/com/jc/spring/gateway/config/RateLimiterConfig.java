package com.jc.spring.gateway.config;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.UUID;

/**
 * @author jincheng.zhang
 */
@Configuration
public class RateLimiterConfig {

    @Bean
    KeyResolver ipKeyResolver(){

        return  e -> Mono.just(Objects.requireNonNull(e.getRequest().getRemoteAddress()).getHostName());
    }
}
