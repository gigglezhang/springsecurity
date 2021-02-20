package com.jc.spring.order;

import com.jc.spring.order.feign.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = OrderApplication.class)
public class test {


    @Autowired
    OrderService orderService;

    @Test
    public void test1(){
        for(int i = 0; i < 10; i++){
            orderService.getOrderId(1);
        }
    }
}
