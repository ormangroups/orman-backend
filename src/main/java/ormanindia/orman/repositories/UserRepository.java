package ormanindia.orman.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import ormanindia.orman.models.User;

public interface UserRepository extends MongoRepository<User,String > {
}
