package ormanindia.orman.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDate;

@Document(collection = "coupons")
public class Coupon {

    @Id
    private String id;

    private String code; // Unique coupon code

    private double discountPercentage; // Discount percentage, e.g., 10.0 for 10%

    private double maxDiscountAmount; // Maximum discount amount in currency

    private LocalDate validFrom; // Start date of coupon validity

    private LocalDate validUntil; // End date of coupon validity

    private boolean isActive; // Whether the coupon is active

    // Getters and Setters
}
