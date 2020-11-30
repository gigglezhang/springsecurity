package com.jc.spring.gateway.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;

import java.util.UUID;

/**
 * @author jincheng.zhang
 */
public class FeignInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate requestTemplate) {
        requestTemplate.header("testAdd", UUID.randomUUID().toString());
    }
}
