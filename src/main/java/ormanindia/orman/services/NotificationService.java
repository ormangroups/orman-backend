package ormanindia.orman.services;

import ormanindia.orman.models.Notification;
import ormanindia.orman.repositories.NotificationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    // Send notification to a single user
    public Notification sendToUser(String recipientId, String message) {
        Notification notification = new Notification();
        notification.setRecipientId(recipientId);
        notification.setMessage(message);
        notification.setRead(false);
        notification.setTimestamp(LocalDateTime.now());
        return notificationRepository.save(notification);
    }

    // Send notification to all users
    @Transactional
    public List<Notification> sendToAll(List<String> userIds, String message) {
        List<Notification> notifications = userIds.stream().map(userId -> {
            Notification notification = new Notification();
            notification.setRecipientId(userId);
            notification.setMessage(message);
            notification.setRead(false);
            notification.setTimestamp(LocalDateTime.now());
            return notification;
        }).collect(Collectors.toList());
        return notificationRepository.saveAll(notifications);
    }

    // Get notifications for a specific user
    public List<Notification> getNotificationsForUser(String recipientId) {
        return notificationRepository.findByRecipientId(recipientId);
    }

    // Mark notification as read
    public boolean markAsRead(String notificationId) {
        Optional<Notification> notificationOptional = notificationRepository.findById(notificationId);
        if (notificationOptional.isPresent()) {
            Notification notification = notificationOptional.get();
            notification.setRead(true);
            notificationRepository.save(notification);
            return true;
        }
        return false;
    }

    // Get all notifications
    public List<Notification> getAllNotifications() {
        return notificationRepository.findAll();
    }

    // Delete notification by ID
    public boolean deleteNotificationById(String id) {
        if (notificationRepository.existsById(id)) {
            notificationRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
