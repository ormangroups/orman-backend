package ormanindia.orman.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "favorites")
@Data
public class Favorite {
    @Id
    private String id;
    private String restaurantId; // Reference to the Restaurant
    private List<Product> favoriteProducts;
}
