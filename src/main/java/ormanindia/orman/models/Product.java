package ormanindia.orman.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "products")
@Data
public class Product {

    @Id
    private String id;
    private String name;
    private String category;
    private String description;
    private Double price;
    private String image;
    private String unit;
    private boolean isAvailable;
    private boolean isCategoryPriceConstant;

}
