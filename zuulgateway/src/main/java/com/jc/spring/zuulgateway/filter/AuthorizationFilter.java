package com.jc.spring.zuulgateway.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jc.spring.zuulgateway.pojo.bean.TokenInfo;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.http.HttpStatus;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.MimeType;
import org.springframework.util.MimeTypeUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author jincheng.zhang
 */
@Component
@Slf4j
public class AuthorizationFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 3;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @SneakyThrows
    @Override
    public Object run() throws ZuulException {
        log.info("Authorization start");
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();

        if(StringUtils.startsWith(request.getRequestURI(),"/token")){
            return null;
        }

        if(isNeedAuth(request)){
            TokenInfo tokenInfo = (TokenInfo) request.getAttribute("tokenInfo");
            if(tokenInfo != null && tokenInfo.isActive()){
                if(!isAuthorization(request,tokenInfo)){
                    log.error("Authorization error is:403 forbidden");
                    handleError(HttpStatus.FORBIDDEN.value(),HttpStatus.FORBIDDEN.getReasonPhrase(),requestContext);
                }
                requestContext.addZuulRequestHeader("username", tokenInfo.getUser_name());
            }else{
                log.error("Authorization error is:401 bad request");
                handleError(HttpStatus.UNAUTHORIZED.value(),HttpStatus.UNAUTHORIZED.getReasonPhrase(),requestContext);
            }
        }
        return null;
    }

    private void handleError(int value, String msg,RequestContext requestContext) throws JsonProcessingException {
        requestContext.setResponseStatusCode(value);
        requestContext.getResponse().setContentType(MimeTypeUtils.APPLICATION_JSON_VALUE);
        Map<String, Object> map = new HashMap<>(2);
        map.put("status", value);
        map.put("msg", msg);
        requestContext.setResponseBody(new ObjectMapper().writeValueAsString(map));
        requestContext.setSendZuulResponse(false);
    }


    private boolean isAuthorization(HttpServletRequest request,TokenInfo tokenInfo) {
        // 假设权限
        return RandomUtils.nextInt() % 2 == 0;
    }

    private boolean isNeedAuth(HttpServletRequest request) {
        return !StringUtils.contains(request.getRequestURI(), "/swagger")
                && !StringUtils.contains(request.getRequestURI(), "/api-docs");
    }
}
