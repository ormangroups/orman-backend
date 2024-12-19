package ormanindia.orman.services;

import ormanindia.orman.models.Notification;
import ormanindia.orman.repositories.NotificationRepository; // Assuming you use a JPA repository
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;

    @Autowired
    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    // Create a new notification
    public Notification createNotification(Notification notification) {
        notification.setRead(false); // Default is unread
        notification.setTimestamp(LocalDateTime.now());
        return notificationRepository.save(notification);
    }

    // Get all notifications
    public List<Notification> getAllNotifications() {
        return notificationRepository.findAll();
    }

    // Get notifications for a specific recipient
    public List<Notification> getNotificationsForUser(String recipientId) {
        return notificationRepository.findByRecipientId(recipientId);
    }

    // Get notifications with null recipientId
    // public List<Notification> getUnassignedNotifications() {
    //     return notificationRepository.findByRecipientIdIsNull();
    // }

    // Mark a notification as read
    public boolean markAsRead(String notificationId) {
        Notification notification = notificationRepository.findById(notificationId).orElse(null);
        if (notification != null) {
            notification.setRead(true);
            notificationRepository.save(notification);
            return true;
        }
        return false;
    }
}
