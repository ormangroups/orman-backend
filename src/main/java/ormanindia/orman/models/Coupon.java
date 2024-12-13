package ormanindia.orman.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDate;

@Document(collection = "coupons")
public class Coupon {

    @Id
    private String id;

    private String code;

    private double discountPercentage;

    private double maxDiscountAmount;

    private LocalDate validFrom;

    private LocalDate validUntil;

    private boolean isActive;
}
