package ormanindia.orman.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import ormanindia.orman.models.Cart;

public interface CartRepository extends MongoRepository<Cart,String > {
}
