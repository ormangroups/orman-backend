package ormanindia.orman.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "carts")
@Data
public class Cart {

    @Id
    private String id;
    private String restaurantId;
    private List<OrderItem> cartItems;

}
