package com.jc.spring.gateway;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;

public class ReactorTest {

    @Test
    public void test(){
        Flux.just("123","hah").subscribe(System.out::println);

    }
}
