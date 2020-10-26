package com.jc.spring.order.pojo.vo;

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
