package ormanindia.orman.models;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
public class OrderItem {
    private Product product;
    private int quantity;
    private double price;




}
