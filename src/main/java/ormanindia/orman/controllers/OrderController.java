package ormanindia.orman.controllers;

import ormanindia.orman.models.Order;
import ormanindia.orman.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    // Create a new order
    @PostMapping("/{restaurantId}")
    public ResponseEntity<Order> createOrder(@RequestBody Order order, @PathVariable String restaurantId) {
        try {
            Order createdOrder = orderService.createOrder(order, restaurantId);
            return ResponseEntity.ok(createdOrder);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    // Get an order by its ID
    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable String id) {
        Optional<Order> order = orderService.getOrderById(id);
        if (order.isPresent()) {
            return ResponseEntity.ok(order.get());
        }
        return ResponseEntity.notFound().build();
    }

    // Get all orders
    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    // Update an order
    @PutMapping("/{id}")
    public ResponseEntity<Order> updateOrder(@PathVariable String id, @RequestBody Order order) {
        try {
            Order updatedOrder = orderService.updateOrder(id, order);
            return ResponseEntity.ok(updatedOrder);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete an order
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable String id) {
        if (orderService.deleteOrder(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    // Get orders by status
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Order>> getOrdersByStatus(@PathVariable String status) {
        try {
            List<Order> orders = orderService.getOrdersByStatus(status);
            return ResponseEntity.ok(orders);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    @GetMapping("/restaurant/{restaurantid}")
    public List<Order> getOrdersByUser(@PathVariable String restaurantid) {
      
            return orderService.getOrdersByrestaurantID(restaurantid);
    
    }

    // Uncomment this method if date range filtering is required
    // @GetMapping("/date-range")
    // public ResponseEntity<List<Order>> getOrdersByDateRange(@RequestParam LocalDate startDate, @RequestParam LocalDate endDate) {
    //     List<Order> orders = orderService.getOrdersByDateRange(startDate, endDate);
    //     return ResponseEntity.ok(orders);
    // }
}
