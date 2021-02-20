import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class test {


    @Autowired
    OrderService orderService;

    @Test
    public void test(){
        for(int i = 0; i < 10; i++){
            orderService.getOrderId(1);
        }
    }
}
