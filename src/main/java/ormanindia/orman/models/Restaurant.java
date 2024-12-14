package ormanindia.orman.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "restaurants")
@Data
public class Restaurant {
    @Id
    private String id;
    private String restaurantName;
    private String restaurantAddress;
    private String contactNumber;
    private String password;
    private String email;
    private List<Product> favList=new ArrayList<>();
    private List<OrderItem> caItems=new ArrayList<>();
    private Boolean isActive = true;
    private Payment payment;
}
