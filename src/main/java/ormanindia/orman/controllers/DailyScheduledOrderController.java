package ormanindia.orman.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ormanindia.orman.models.OrderItem;
import ormanindia.orman.services.RestaurantService;

import java.util.List;

@RestController
@RequestMapping("/api/daily-scheduled-orders")
public class DailyScheduledOrderController {

    @Autowired
    private RestaurantService restaurantService;

    @GetMapping("/{restaurantId}")
    public ResponseEntity<List<OrderItem>> getDailyScheduledProducts(@PathVariable String restaurantId) {
        List<OrderItem> dailyProducts = restaurantService.getdailyProducts(restaurantId);
        return ResponseEntity.ok(dailyProducts);
    }

    @PostMapping("/{restaurantId}")
    public ResponseEntity<Void> addToDailyScheduledItems(@PathVariable String restaurantId, @RequestBody OrderItem orderItem) {
        restaurantService.addToDailyScheduledItems(restaurantId, orderItem);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{restaurantId}/{productId}")
    public ResponseEntity<Void> removeFromDailyScheduledItems(@PathVariable String restaurantId, @PathVariable String productId) {
        restaurantService.removeFromDailyScheduledItems(restaurantId, productId);
        return ResponseEntity.noContent().build();
    }
}
