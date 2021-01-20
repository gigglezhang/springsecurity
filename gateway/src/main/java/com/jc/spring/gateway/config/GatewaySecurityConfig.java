package com.jc.spring.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.NimbusReactiveJwtDecoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

/**
 * @author jincheng.zhang
 */


@EnableWebFluxSecurity
public class GatewaySecurityConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        http.authorizeExchange()
                .pathMatchers("/token/**").permitAll()
                .anyExchange().authenticated()
                .and()
            .oauth2ResourceServer().jwt();

        return http.build();
    }



    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/token/**").permitAll()
                .anyRequest().authenticated();
    }
}
