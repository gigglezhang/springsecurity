import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "test-f", url = "localhost:8001")
public interface OrderService {
    @GetMapping("/order/{id}")
    String getOrderId(@PathVariable int id);
}
