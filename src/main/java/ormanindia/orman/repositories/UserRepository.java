package ormanindia.orman.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import ormanindia.orman.models.User;

public interface UserRepository extends MongoRepository<User,String > {
    public User findByusername(String username);
}
