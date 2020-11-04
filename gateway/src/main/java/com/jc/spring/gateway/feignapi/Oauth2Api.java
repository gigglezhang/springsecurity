package com.jc.spring.gateway.feignapi;

import com.jc.spring.gateway.bean.TokenInfo;
import com.jc.spring.gateway.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author jincheng.zhang
 */
@FeignClient(url = "localhost:9090",name = "oauth2",configuration = FeignConfig.class)
public interface Oauth2Api {
    /**
     * getTokenInfo
     * @param map token
     * @return TokenInfo
     */
    @PostMapping(path = "/oauth/check_token", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    TokenInfo getTokenInfo(MultiValueMap<String, String> map);
}
