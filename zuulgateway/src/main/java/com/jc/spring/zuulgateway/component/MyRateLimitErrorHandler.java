package com.jc.spring.zuulgateway.component;

import com.marcosbarbero.cloud.autoconfigure.zuul.ratelimit.config.repository.DefaultRateLimiterErrorHandler;

/**
 * @author jincheng.zhang
 */
public class MyRateLimitErrorHandler extends DefaultRateLimiterErrorHandler {
    @Override
    public void handleSaveError(String key, Exception e) {
        super.handleSaveError(key, e);
    }

    @Override
    public void handleFetchError(String key, Exception e) {
        super.handleFetchError(key, e);
    }

    @Override
    public void handleError(String msg, Exception e) {
        super.handleError(msg, e);
    }
}
