package com.jc.spring.order.controller;

import com.jc.spring.order.pojo.vo.OrderInfo;
import com.jc.spring.order.pojo.vo.PriceInfo;
import com.jc.spring.order.pojo.vo.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

/**
 * @author jincheng.zhang
 */
@RestController
@RequestMapping("/order")
@Slf4j
public class OrderController {
    private RestTemplate restTemplate  = new RestTemplate();

    @PostMapping
    public OrderInfo createOrder(@RequestBody OrderInfo info, @AuthenticationPrincipal User user){
//        PriceInfo priceInfo = restTemplate.getForObject("http://localhost:8002/prices/" + info.getProductId(),PriceInfo.class);
//        log.info("prices is {}", priceInfo != null ? priceInfo.getPrice(): "" );
        log.info("user is {}", user.getId());
        return info;
    }

    @GetMapping("{idd}")
    public OrderInfo getOrder(@PathVariable Long idd, @AuthenticationPrincipal(expression = "id") Long id){
        OrderInfo info = new OrderInfo();
        info.setProductId(id);
        log.info("user is {}", id);
        return info;
    }
}
