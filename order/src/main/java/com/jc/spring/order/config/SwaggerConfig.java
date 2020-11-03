package com.jc.spring.order.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.OAuthBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author jincheng.zhang
 */
@Configuration
public class SwaggerConfig {
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
//                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .apis(RequestHandlerSelectors.basePackage("com.jc.spring.order.controller"))
                .paths(PathSelectors.any())
                .build()
                .securitySchemes(Collections.singletonList(oauth2()))
                .securityContexts(Collections.singletonList(securityContexts()));
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("order")
                .description("order api")
                .contact(new Contact("jc", "http://www.baidu.com", "xiaokawayixiao@qq.com"))
                .version("1.0")
                .build();
    }


    private SecurityContext securityContexts() {
        return SecurityContext.builder()
                .securityReferences(Collections.singletonList(new SecurityReference("Authorization", scopes())))
                .forPaths(PathSelectors.any())
                .build();
    }


    private SecurityScheme oauth2(){

//         granType
//        TokenRequestEndpoint tokenRequestEndpoint = new TokenRequestEndpoint("http://localhost:8080/token/oauth/token",
//                "orderApp", "123456");
//        TokenEndpoint tokenEndpoint = new TokenEndpoint("http://localhost:8080/token/oauth/check_token", "token");
//        GrantType grantType = new ;
        // 密码模式使用这个
        GrantType grantType = new ResourceOwnerPasswordCredentialsGrant("http://localhost:8080/token/oauth/token");
        List<GrantType> grantTypes = Collections.singletonList(grantType);
        return  new OAuthBuilder().name("Authorization").scopes(Arrays.asList(scopes())).grantTypes(grantTypes).build();
    }

    private AuthorizationScope[] scopes(){
         return new AuthorizationScope[]{
                 new AuthorizationScope("read", "read"),
                 new AuthorizationScope("write", "write")
         };
    }




}
