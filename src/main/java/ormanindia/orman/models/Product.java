package ormanindia.orman.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;


import java.util.Date;


@Document(collection = "products")
@Data
public class Product {

    @Id
    private String id;
    private String name;
    private String category;
    private String description;
    private double price;
    private String image;
    private boolean isAvailable;


}