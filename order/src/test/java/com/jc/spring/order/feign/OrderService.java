package com.jc.spring.order.feign;

import feign.Logger;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import feign.auth.BasicAuthRequestInterceptor;
import io.netty.handler.logging.LogLevel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name = "com.jc.spring.order.test-f", url = "localhost:8001", configuration = OrderService.Config.class)
public interface OrderService {
    @GetMapping("/order/{id}")
    String getOrderId(@PathVariable int id);

    class Config implements RequestInterceptor {
        // 这个@Bean是必须的
        @Bean
        Logger.Level feignLoggerLevel(){
            return Logger.Level.BASIC;
        }

        @Override
        public void apply(RequestTemplate template) {
            template.header("Authorization", "Bearer " + "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsib3JkZXItc2VydmVyIl0sInVzZXJfbmFtZSI6ImpjIiwic2NvcGUiOlsicmVhZCIsIndyaXRlIiwiZmx5Il0sImV4cCI6MTYxNDA2NjQ1MywiYXV0aG9yaXRpZXMiOlsiUk9MRV9BRE1JTiJdLCJqdGkiOiJmMGRjYWUyMS1mYzRkLTQzOWEtOWZiMy05OWRmNGRlZjdmYTQiLCJjbGllbnRfaWQiOiJvcmRlckFwcCJ9.owQVpkbmtZANJgbTYLIo_8-lJaYM05aYrbm2Q3FoxNp6SCGhUHGmzGYA185RJVM0KM5a4rU7Gvy4GazQYBmkAnx-G2uPrrr_TXC3fT1QX_UZFATcglJWkcgoVljuYJdbWZV_E7Pr09KuNCDFbVIxpPjHEhKXStcmT1J-fqTs_063ajTYwA49tuZosKxjjQMnG9WRJ0CkBY2vhQFLHarJ9JBMhOxn7h-G8kKtnsvfZmfsxT3tE3jN1c_yT3oDpBdvFzhOwkkzIZ4gOyLhRKlCAW_YwtQhGcO4hlRvvP0zMBgc72MXGf9j38GnbaZCg_QO-CLSEPctxl1mBQHR3evJPw");
        }
    }
}
