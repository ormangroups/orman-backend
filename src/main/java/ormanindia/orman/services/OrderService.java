package ormanindia.orman.services;

import ormanindia.orman.models.Order;
import ormanindia.orman.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    // Create a new order
    public Order createOrder(Order order) {
        order.calculateTotalPrice();  // Make sure to calculate the total price before saving
        return orderRepository.save(order);
    }

    // Get an order by its ID
    public Optional<Order> getOrderById(String id) {
        return orderRepository.findById(id);
    }

    // Get all orders
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    // Update an order
    public Order updateOrder(String id, Order order) {
        Optional<Order> existingOrder = orderRepository.findById(id);
        if (existingOrder.isPresent()) {
            Order updatedOrder = existingOrder.get();
            updatedOrder.setStatus(order.getStatus());  // Update only specific fields
            updatedOrder.setAppliedCoupon(order.getAppliedCoupon());
            updatedOrder.calculateTotalPrice();  // Recalculate total price after any changes
            return orderRepository.save(updatedOrder);
        }
        return null; // Or throw an exception based on your needs
    }

    // Delete an order by ID
    public boolean deleteOrder(String id) {
        if (orderRepository.existsById(id)) {
            orderRepository.deleteById(id);
            return true;
        }
        return false; // Could be enhanced to throw an exception if not found
    }
}
