package ormanindia.orman.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import ormanindia.orman.models.Order;

public interface OrderRepository extends MongoRepository<Order,String> {
}
