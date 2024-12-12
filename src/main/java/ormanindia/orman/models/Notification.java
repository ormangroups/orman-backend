package ormanindia.orman.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "notifications")
@Data
public class Notification {

    @Id
    private String id;
    private String recipientId; // ID of the recipient restaurant
    private String message; // Notification content
    private boolean isRead; // Read/Unread status
    private LocalDateTime timestamp; // Time when the notification was created


}
