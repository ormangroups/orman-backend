package ormanindia.orman.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ormanindia.orman.models.Restaurant;


@Repository
public interface RestaurantRepository extends MongoRepository<Restaurant, String> {
    // Add custom query methods if needed
}
