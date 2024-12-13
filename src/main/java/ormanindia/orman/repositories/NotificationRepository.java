package ormanindia.orman.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import ormanindia.orman.models.Notification;

public interface NotificationRepository extends MongoRepository<Notification,String> {
}
