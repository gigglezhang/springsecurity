package com.jc.spring.prices.controller;

import com.jc.spring.prices.pojo.vo.PriceInfo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

/**
 * @author jincheng.zhang
 */
@RestController
@RequestMapping("/prices")
public class PriceController {
    @GetMapping("/{id}")
    public PriceInfo getPriceInfo(@PathVariable Long id){
        // fix
        PriceInfo priceInfo = new PriceInfo();
        priceInfo.setId(id);
        priceInfo.setPrice(new BigDecimal("100"));
        return priceInfo;
    }
}
