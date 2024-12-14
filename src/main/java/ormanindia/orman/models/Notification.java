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
    private String recipientId;
    private String message;
    private boolean isRead;
    private LocalDateTime timestamp;


}
