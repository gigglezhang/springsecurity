package com.jc.spring.prices.pojo.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author jincheng.zhang
 */
@Data
public class PriceInfo {
    private Long id;
    private BigDecimal price;
}
