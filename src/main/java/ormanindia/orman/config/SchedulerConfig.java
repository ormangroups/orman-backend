package ormanindia.orman.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import ormanindia.orman.services.OrderService;

@Configuration
@EnableScheduling
public class SchedulerConfig {

    @Autowired
    private OrderService orderService;

    @Scheduled(cron = "0 0 21 * * *") 
    public void scheduleDailyOrders() {
        orderService.placeDailyOrders();
    }
}
