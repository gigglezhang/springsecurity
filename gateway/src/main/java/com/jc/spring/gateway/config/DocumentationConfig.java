package com.jc.spring.gateway.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.config.GatewayProperties;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.support.NameUtils;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jincheng.zhang
 */
@Component
@Primary
@Slf4j
public class DocumentationConfig implements SwaggerResourcesProvider {

    @Autowired
    private GatewayProperties gatewayProperties;

    // 自动获取系统配置的路由资源集合
    @Override
    public List<SwaggerResource> get() {
        List<SwaggerResource> resources = new ArrayList<>();
        gatewayProperties.getRoutes().stream().filter(
                routeDefinition -> !"oauth2".equals(routeDefinition.getId())
        ).forEach(routeDefinition -> {
            routeDefinition.getPredicates().stream()
                    .filter( predicateDefinition -> "Path".equals(predicateDefinition.getName()))
                    .forEach(predicateDefinition -> {
                        resources.add(swaggerResource(routeDefinition.getId(),
                                predicateDefinition.getArgs().get(NameUtils.GENERATED_NAME_PREFIX + "0").replace("/**","/v2/api-docs")));
                    });

        });

        return resources;
    }

    // 获取对应的路由资源
    private SwaggerResource swaggerResource(String name, String location) {
        SwaggerResource swaggerResource = new SwaggerResource();
        swaggerResource.setName(name);
        swaggerResource.setLocation(location);
        swaggerResource.setSwaggerVersion("2.0");
        return swaggerResource;
    }

}

