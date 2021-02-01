package com.jc.spring.order.controller;

import com.jc.spring.order.pojo.vo.OrderInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
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

//    @PostMapping
//    public OrderInfo createOrder(@RequestBody OrderInfo info, @AuthenticationPrincipal String username){
////        PriceInfo priceInfo = restTemplate.getForObject("http://localhost:8002/prices/" + info.getProductId(),PriceInfo.class);
////        log.info("prices is {}", priceInfo != null ? priceInfo.getPrice(): "" );
//        log.info("user is {}", username);
//        return info;
//    }

    @GetMapping("{id}")
    @PreAuthorize("#oauth2.hasScope('fly')")
    public OrderInfo getOrder(@PathVariable Long id,@AuthenticationPrincipal String username){
        OrderInfo info = new OrderInfo();
        info.setProductId(id);
        log.info("user is {}", username);
        return info;
    }
}
