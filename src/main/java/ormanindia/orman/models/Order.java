package ormanindia.orman.models;


import lombok.Data;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "orders")
@Data

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

    public void calculateTotalPrice() {
        totalPrice = items.stream().mapToDouble(OrderItem::getPrice).sum();

        if (appliedCoupon != null && appliedCoupon.isActive() && !LocalDate.now().isBefore(appliedCoupon.getValidFrom()) && !LocalDate.now().isAfter(appliedCoupon.getValidUntil())) {

            double discount = (totalPrice * appliedCoupon.getDiscountPercentage()) / 100;

            discountAmount = Math.min(discount, appliedCoupon.getMaxDiscountAmount());
            finalAmount = totalPrice - discountAmount;
        } else {
            discountAmount = 0;
            finalAmount = totalPrice;
        }
    }



}
