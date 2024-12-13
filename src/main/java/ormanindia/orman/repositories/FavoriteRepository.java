package ormanindia.orman.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import ormanindia.orman.models.Favorite;

public interface FavoriteRepository extends MongoRepository<Favorite,String > {
}
