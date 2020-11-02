package com.jc.spring.zuulgateway.filter;

import com.jc.spring.zuulgateway.pojo.bean.TokenInfo;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;

/**
 * @author jincheng.zhang
 */
@Slf4j
@Component
public class Oauth2AuthenticationFilter extends ZuulFilter {
    private RestTemplate restTemplate = new RestTemplate();

    /**
     * 过滤类型
     * @return String
     */
    @Override
    public String filterType() {
        // pre , post , error ,route
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    /**
     * 判断是否需要过滤
     * @return boolean
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        log.info("Authorization start");
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        String uri = request.getRequestURI();
        // 发给oauth2 的通过
        if(StringUtils.startsWith(uri, "/token")){
            return null;
        }
        String authorization = request.getHeader("Authorization");
        // 没有带认证的不做身份认证。ps: 流程是认证都会往后走
        if(StringUtils.isBlank(authorization)){
            return null;
        }
        // 如果不是bearer 类型的也不做认证 ps: 流程是认证都会往后走
        if(!StringUtils.startsWithIgnoreCase(authorization, "Bearer ")){
            return  null;
        }
        try {
            TokenInfo tokenInfo = getTokenInfo(authorization);
            request.setAttribute("tokenInfo", tokenInfo);
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        return null;
    }

    private TokenInfo getTokenInfo(String authHeader){
        String url = "http://localhost:9090/oauth/check_token";
        String token = StringUtils.substringAfter(authHeader, "Bearer ");
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        // 这个要加
        httpHeaders.setBasicAuth("gateway","123456");
        MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();

        multiValueMap.add("token", token);
        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(multiValueMap,httpHeaders);
        ResponseEntity<TokenInfo> responseEntity  = restTemplate.exchange(url, HttpMethod.POST,httpEntity,TokenInfo.class);
        TokenInfo tokenInfo = null;
        if(responseEntity.getStatusCode().equals(HttpStatus.OK)){
            tokenInfo = responseEntity.getBody();
            if (tokenInfo != null){
                log.info(tokenInfo.toString());
            }
        }

        return tokenInfo;
    }

}
