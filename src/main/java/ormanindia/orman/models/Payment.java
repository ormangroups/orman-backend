package ormanindia.orman.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import ormanindia.orman.models.PaymentTransaction;

import java.util.ArrayList;
import java.util.List;


@Data
public class Payment {

    private double totalAmount;


    private double paidAmount;

    private double pendingAmount;

    private List<PaymentTransaction> pastSettlements = new ArrayList<>();


}
