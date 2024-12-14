package ormanindia.orman.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import ormanindia.orman.models.Order;
import java.util.List;


public interface OrderRepository extends MongoRepository<Order,String> {
    List<Order> findByStatus(String status);
}
