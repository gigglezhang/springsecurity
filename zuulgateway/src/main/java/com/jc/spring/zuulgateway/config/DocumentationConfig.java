package com.jc.spring.zuulgateway.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.Route;
import org.springframework.cloud.netflix.zuul.filters.RouteLocator;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
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
    private RouteLocator routeLocator;


    // 自动获取系统配置的路由资源集合
    @Override
    public List<SwaggerResource> get() {
        List<SwaggerResource> resources = new ArrayList<>();
        List<Route> routes = routeLocator.getRoutes();
        routes.stream().filter(route -> !"token".equals(route.getId())).forEach(route -> {
            log.info("fullpath: {},location: {}",route.getFullPath(), route.getLocation());
            resources.add(swaggerResource(route.getId(),
                    route.getFullPath().replace("/**", "/v2/api-docs"),"1.0"));
        });

        return resources;
    }

    // 获取对应的路由资源
    private SwaggerResource swaggerResource(String name, String location, String version) {
        SwaggerResource swaggerResource = new SwaggerResource();
        swaggerResource.setName(name);
        swaggerResource.setLocation(location);
        swaggerResource.setSwaggerVersion(version);
        return swaggerResource;
    }

}

