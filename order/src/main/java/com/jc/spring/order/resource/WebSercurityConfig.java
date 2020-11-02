//package com.jc.spring.order.resource;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.context.annotation.Bean;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationManager;
//import org.springframework.security.oauth2.provider.token.*;
//
//@EnableWebSecurity
//public class WebSercurityConfig extends WebSecurityConfigurerAdapter {
//
//    @Qualifier("userDetailsServiceImpl")
//    @Autowired
//    UserDetailsService userDetailsService;
//
//    @Bean
//    ResourceServerTokenServices tokenServices() {
//        RemoteTokenServices remoteTokenServices = new RemoteTokenServices();
//        remoteTokenServices.setClientId("orderApp");
//        remoteTokenServices.setClientSecret("123456");
//        remoteTokenServices.setCheckTokenEndpointUrl("http://localhost:9090/oauth/check_token");
//        remoteTokenServices.setAccessTokenConverter(accessTokenConverter());
//        return remoteTokenServices;
//    }
//
//    private AccessTokenConverter accessTokenConverter(){
//        DefaultAccessTokenConverter defaultAccessTokenConverter = new DefaultAccessTokenConverter();;
//
//        DefaultUserAuthenticationConverter defaultUserAuthenticationConverter = new DefaultUserAuthenticationConverter();
//        defaultUserAuthenticationConverter.setUserDetailsService(userDetailsService);
//        defaultAccessTokenConverter.setUserTokenConverter(defaultUserAuthenticationConverter);
//        return defaultAccessTokenConverter;
//    }
//
//    @Override
//    public AuthenticationManager authenticationManagerBean() throws Exception {
//        OAuth2AuthenticationManager authenticationManager = new OAuth2AuthenticationManager();
//        authenticationManager.setTokenServices(tokenServices());
//        return authenticationManager;
//    }
//}
