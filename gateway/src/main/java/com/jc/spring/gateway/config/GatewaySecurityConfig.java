package com.jc.spring.gateway.config;

import com.jc.spring.gateway.bean.PublicKeyInfo;
import com.jc.spring.gateway.component.GatewayAuthenticationEntryPoint;
import com.jc.spring.gateway.component.Oauth2AccessDeniedHandler;
import com.jc.spring.gateway.component.MyReactiveAuthorizationManager;
import com.jc.spring.gateway.feignapi.Oauth2Api;
import com.jc.spring.gateway.filter.GatewayAuditLogFilter;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.NimbusReactiveJwtDecoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.util.Base64Utils;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

/**
 * @author jincheng.zhang
 */


@EnableWebFluxSecurity
@Slf4j
@AllArgsConstructor
public class GatewaySecurityConfig {



    private final Oauth2Api oauth2Api;

    private final MyReactiveAuthorizationManager myReactiveAuthorizationManager;

    private final Oauth2AccessDeniedHandler oauth2AccessDeniedHandler;
    private final GatewayAuthenticationEntryPoint gatewayAuthenticationEntryPoint;
    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        http.csrf().disable()
                .authorizeExchange()
                .pathMatchers("/token/**").permitAll()
                .anyExchange().access(myReactiveAuthorizationManager)
                .and()

            .addFilterBefore(new GatewayAuditLogFilter(), SecurityWebFiltersOrder.EXCEPTION_TRANSLATION)
            .oauth2ResourceServer(oAuth2ResourceServerSpec ->{
                    oAuth2ResourceServerSpec.jwt(jwtSpec -> jwtSpec.jwtDecoder(jwtDecoder()));
                    oAuth2ResourceServerSpec.authenticationEntryPoint(gatewayAuthenticationEntryPoint);
                    oAuth2ResourceServerSpec.accessDeniedHandler(oauth2AccessDeniedHandler);

                }
            );



        return http.build();
    }

    @Bean
    ReactiveJwtDecoder jwtDecoder(){

//        Mono<String> publicKey = WebClient.create(uri)
//                .get()
//                .headers(httpHeaders -> httpHeaders.setBasicAuth("gateway","123456"))
//                .retrieve()
//                .bodyToMono(String.class)
//                .map(s -> {
//                    String key = null;
//                    try {
//                        JsonNode jsonNode = new ObjectMapper().readTree(s);
//                        key =  jsonNode.get("publick").toString();
//                    } catch (JsonProcessingException e) {
//                        e.printStackTrace();
//                    }
//                    return key;
//                });

        RSAPublicKey rsaPublicKey = null;
        PublicKeyInfo publicKeyInfo = oauth2Api.getPublicKey();
        try {
            // 去除前后缀
            String value = publicKeyInfo.getValue().replace("-----BEGIN PUBLIC KEY-----\n","")
                    .replace("\n-----END PUBLIC KEY-----","");
            log.info(value);
            // 解密由base64编码的公钥
            byte[] keyBytes = Base64Utils.decodeFromString(value);
            // 构造X509EncodedKeySpec对象
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
            // KEY_ALGORITHM 指定的加密算法
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            // 取公钥匙对象
            rsaPublicKey = (RSAPublicKey) keyFactory.generatePublic(keySpec);
        } catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        assert rsaPublicKey != null;
        return NimbusReactiveJwtDecoder.withPublicKey(rsaPublicKey).build();
    }
}
