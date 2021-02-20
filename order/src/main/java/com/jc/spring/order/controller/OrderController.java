package com.jc.spring.order.controller;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.util.TimeUtil;
import com.jc.spring.order.pojo.vo.OrderInfo;
import io.micrometer.core.instrument.util.TimeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.TimeUnit;

/**
 * @author jincheng.zhang
 */
@RestController
@RequestMapping("/order")
@Slf4j
public class OrderController {
    private RestTemplate restTemplate  = new RestTemplate();

//    @PostMapping
//    public OrderInfo createOrder(@RequestBody OrderInfo info, @AuthenticationPrincipal String username){
////        PriceInfo priceInfo = restTemplate.getForObject("http://localhost:8002/prices/" + info.getProductId(),PriceInfo.class);
////        log.info("prices is {}", priceInfo != null ? priceInfo.getPrice(): "" );
//        log.info("user is {}", username);
//        return info;
//    }

    @GetMapping("{id}")
    @PreAuthorize("#oauth2.hasScope('fly')")
    @SentinelResource(value = "orderId" )
    public OrderInfo getOrder(@PathVariable Long id,@AuthenticationPrincipal String username) throws InterruptedException {
        OrderInfo info = null;

        info = new OrderInfo();
        info.setProductId(id);
        TimeUnit.MILLISECONDS.sleep(50);
        log.info("user is {}", username);
        return info;
    }
}
