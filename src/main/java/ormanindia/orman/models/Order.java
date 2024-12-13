package ormanindia.orman.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "orders")
public class Order {

    @Id
    private String id;

    private List<OrderItem> items;

    private LocalDateTime orderDate;

    private double totalPrice;

    private String status;

    private Coupon appliedCoupon;

    private double discountAmount;
    private double finalAmount;



}