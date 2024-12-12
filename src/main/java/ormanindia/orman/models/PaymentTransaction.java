package ormanindia.orman.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
@Document("PaymentTransaction")
@Data
public class PaymentTransaction {

    private double amountPaid;

    private LocalDateTime transactionDate;
    @Id
    private String id;


}
