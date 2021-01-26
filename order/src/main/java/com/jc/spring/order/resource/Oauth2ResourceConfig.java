package com.jc.spring.order.resource;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

/**
 * @author jincheng.zhang
 */
@EnableResourceServer
@Configuration
public class Oauth2ResourceConfig extends ResourceServerConfigurerAdapter {
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        // 需要和oauth2 中配置的一致
        resources.resourceId("order-server");
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.formLogin().disable().logout().disable().csrf().disable().
                authorizeRequests().anyRequest().authenticated();
    }
}
