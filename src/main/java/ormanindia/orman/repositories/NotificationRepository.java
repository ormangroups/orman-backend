package ormanindia.orman.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import ormanindia.orman.models.Notification;

public interface NotificationRepository extends MongoRepository<Notification,String> {
    List<Notification> findByRecipientId(String recipientId);
    //List<Notification> findByRecipientIdIsNull();
}
