package com.jc.spring.order;

import com.jc.spring.order.feign.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.Stream;

@SpringBootTest(classes = OrderApplication.class)
public class TestSentinel {


    @Autowired
    OrderService orderService;

    @Test
    public void test1(){
        Stream.iterate(0, i->i+1).limit(10).parallel().forEach(i -> System.out.println(orderService.getOrderId(i))
        );
    }
}
