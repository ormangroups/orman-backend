package ormanindia.orman.repositories;


import org.springframework.data.mongodb.repository.MongoRepository;
import ormanindia.orman.models.Product;

public interface ProductRepository extends MongoRepository<Product,String > {
}
