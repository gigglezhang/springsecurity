package com.jc.spring.oauth2.config;

import com.jc.spring.oauth2.component.UserDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.endpoint.TokenKeyEndpoint;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import javax.sql.DataSource;
import javax.xml.crypto.Data;

/**
 * @author jincheng.zhang
 */

@Configuration
@EnableAuthorizationServer
public class Ouath2ServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserDetailServiceImpl userDetailService;

    @Autowired
    DataSource dataSource;

    @Bean
    public TokenStore tokenStore(DataSource dataSource){
//        return new JdbcTokenStore(dataSource);
        return new JwtTokenStore(jwtTokenEnhancer());
    }

    @Bean
    public JwtAccessTokenConverter jwtTokenEnhancer(){
        JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(new ClassPathResource("jccc.keystore"),"123456".toCharArray());
        jwtAccessTokenConverter.setKeyPair(keyStoreKeyFactory.getKeyPair("jccc"));
        return  jwtAccessTokenConverter;
    }


    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        // 验证token时需要的认证，带上orderApp 密码123456
        security.checkTokenAccess("isAuthenticated()")
                // 通过认证拿到tokenkey
                .checkTokenAccess("isAuthenticated()");
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
//        clients.inMemory()
//                .withClient("orderApp")
//                .secret(passwordEncoder.encode("123456"))
//                .scopes("read","write")
//                .accessTokenValiditySeconds(3600)
//                // 这个代表资源服务器的Id
//                .resourceIds("order-server")
//                .and()
//                .withClient("orderServer")
//                .secret(passwordEncoder.encode("123456"))
//                .scopes("read")
//                .accessTokenValiditySeconds(3600)
//                .resourceIds("order-server");
        clients.jdbc(dataSource);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.userDetailsService(userDetailService)
                .authenticationManager(authenticationManager)
                .tokenStore(tokenStore(dataSource))
                .tokenEnhancer(jwtTokenEnhancer());
    }

//    @Bean
//    public TokenKeyEndpoint tokenKeyEndpoint() {
//        return new TokenKeyEndpoint(jwtTokenEnhancer());
//    }

}
