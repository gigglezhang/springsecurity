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
        @Bean
        Logger.Level feignLoggerLevel(){
            return Logger.Level.FULL;
        }

        @Override
        public void apply(RequestTemplate template) {
            template.header("Authorization", "Bearer " + "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsib3JkZXItc2VydmVyIl0sInVzZXJfbmFtZSI6ImpjIiwic2NvcGUiOlsicmVhZCIsIndyaXRlIiwiZmx5Il0sImV4cCI6MTYxMzgxNjcyMiwiYXV0aG9yaXRpZXMiOlsiUk9MRV9BRE1JTiJdLCJqdGkiOiI0OGE1ZDYxMS0wNTBiLTQxMGItOGZlNC1hZTkwNjJkMzQ2NTUiLCJjbGllbnRfaWQiOiJvcmRlckFwcCJ9.T2ZIviW1rvkENhG_uRutgz50-pPogl9iMtzmw-cobCMUt9up2Rgs2wGHx7lXnEYM12CNOnPpdenQ7ha7wlecxhGUkSbUpCJWyLjKStKWf5HVpe1LXnH0Z3iU8wsi_SVeNy5LP_vVpKxKDZ-MhbpoNFHKyHyEd22bMq0LXUII_uom9txalEElIf4n2kEwTlpn8YbYeLl0cDY7QpVAYUVJUxJgaPhebEiSkf2MEAtIXm6pSBHV1he-VebylPlSqbu_IFo2g9M61xoLkaxrXpb97O6NTRxGeAfTucvGOiPbXFrmi4XA6jG0UDUzFh47-Jo9qxa_WvTh6k2Bjh6fiVuS1w");
        }
    }
}
