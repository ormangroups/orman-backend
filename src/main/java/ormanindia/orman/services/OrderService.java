package ormanindia.orman.services;

import ormanindia.orman.models.Order;
import ormanindia.orman.models.OrderItem;
import ormanindia.orman.models.Restaurant;
import ormanindia.orman.models.Payment;
import ormanindia.orman.repositories.OrderRepository;
import ormanindia.orman.repositories.RestaurantRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private RestaurantService restaurantService;
    @Autowired
    private RestaurantRepository restaurantRepository;
    @Autowired
    MailService mailService;

    // Create a new order and update restaurant payment fields
    public Order createOrder(Order order, String restaurantId) {
        // Set order date and calculate total price
        order.setOrderDate(LocalDateTime.now()); // Set the current date and time for the order
        order.calculateTotalPrice();           // Ensure the total price is calculated
        order.setStatus("PENDING");
        
        // Save the order
        Order savedOrder = orderRepository.save(order);

        // Update the restaurant's payment fields
        Restaurant restaurant = restaurantService.getRestaurantById(restaurantId);
        if (restaurant != null) {
            Payment payment = restaurant.getPayment();
            if (payment == null) {
                payment = new Payment();
                restaurant.setPayment(payment);
            }

            payment.setTotalAmount(payment.getTotalAmount() + order.getFinalAmount());
            payment.setPendingAmount(payment.getPendingAmount() + order.getFinalAmount());
            
            // Save the updated restaurant
            restaurant.getCaItems().clear();
            restaurantRepository.save(restaurant);
        }
        String emailContent = mailService.buildOrderConfirmationEmail(savedOrder);  
        mailService.sendEmail(restaurant.getEmail(), "Order Confirmation", emailContent);

        return savedOrder;
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
            // Update specific fields
            updatedOrder.setStatus(order.getStatus());
            updatedOrder.setAppliedCoupon(order.getAppliedCoupon());
            updatedOrder.calculateTotalPrice();
            return orderRepository.save(updatedOrder);
        }
        throw new IllegalArgumentException("Order not found with ID: " + id);
    }

    // Delete an order by ID
    public boolean deleteOrder(String id) {
        if (orderRepository.existsById(id)) {
            orderRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Get orders by status
    public List<Order> getOrdersByStatus(String status) {
        if (status == null || status.isEmpty()) {
            throw new IllegalArgumentException("Status cannot be null or empty");
        }
        return orderRepository.findByStatus(status);
    }
    public List<Order> getOrdersByrestaurantID(String restaurantID) {
    
        return orderRepository.findByrestaurantID(restaurantID);
    }
     
     public void placeDailyOrders() { 
        List<Restaurant> restaurants = restaurantRepository.findAll(); 
        for (Restaurant restaurant : restaurants) {
            List<OrderItem> dailyItems = restaurant.getDailyScheduledItems();
            if (!dailyItems.isEmpty()) {
                    Order order = new Order();
                    order.setRestaurantID(restaurant.getId()); 
                    order.setOrderDate(LocalDateTime.now()); 
                    order.setItems(dailyItems);
                    order.setStatus("Pending"); 
                    double totalAmount = dailyItems.stream().mapToDouble(item -> item.getPrice() * item.getQuantity()).sum(); order.setTotalPrice(totalAmount); order.setFinalAmount(totalAmount); 
                    Order savedOrder=orderRepository.save(order); 
                    Payment payment = restaurant.getPayment(); 
                    if (payment == null) { 
                        payment = new Payment(); 
                        restaurant.setPayment(payment); 
                    } 
                   
                    payment.setTotalAmount(payment.getTotalAmount() + order.getFinalAmount());
                    payment.setPendingAmount(payment.getPendingAmount() + order.getFinalAmount());
                    restaurantRepository.save(restaurant);
                
                    String emailContent = mailService.buildOrderConfirmationEmail(savedOrder);  
                    mailService.sendEmail(restaurant.getEmail(), "Order Confirmation", emailContent);
                }
        }
     }

    // Get orders within a date range
    // public List<Order> getOrdersByDateRange(LocalDate startDate, LocalDate endDate) {
    //     LocalDateTime startDateTime = startDate.atStartOfDay();
    //     LocalDateTime endDateTime = endDate.plusDays(1).atStartOfDay(); // Inclusive of end date
    //     return orderRepository.findByOrderDateBetween(startDateTime, endDateTime);
    // }
}
