package ormanindia.orman.controllers;

import ormanindia.orman.models.Notification;
import ormanindia.orman.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    @Autowired
    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    // Send notification to a single user
    @PostMapping("/send-to-user")
    public ResponseEntity<Notification> sendToUser(@RequestParam String recipientId, @RequestParam String message) {
        Notification notification = notificationService.sendToUser(recipientId, message);
        return ResponseEntity.ok(notification);
    }

    // Send notification to all users
    @PostMapping("/send-to-all")
    public ResponseEntity<List<Notification>> sendToAll(@RequestBody List<String> userIds, @RequestParam String message) {
        List<Notification> notifications = notificationService.sendToAll(userIds, message);
        return ResponseEntity.ok(notifications);
    }

    // Get notifications for a specific user
    @GetMapping("/user/{recipientId}")
    public ResponseEntity<List<Notification>> getNotificationsForUser(@PathVariable String recipientId) {
        List<Notification> notifications = notificationService.getNotificationsForUser(recipientId);
        return ResponseEntity.ok(notifications);
    }

    // Mark notification as read
    @PutMapping("/mark-as-read/{notificationId}")
    public ResponseEntity<String> markAsRead(@PathVariable String notificationId) {
        boolean success = notificationService.markAsRead(notificationId);
        if (success) {
            return ResponseEntity.ok("Notification marked as read.");
        } else {
            return ResponseEntity.badRequest().body("Notification not found.");
        }
    }

    // Get all notifications
    @GetMapping("/all")
    public ResponseEntity<List<Notification>> getAllNotifications() {
        List<Notification> notifications = notificationService.getAllNotifications();
        return ResponseEntity.ok(notifications);
    }

    // Delete notification by ID
    // @DeleteMapping("/delete/{id}")
    // public ResponseEntity<String> deleteNotification(@PathVariable String id) {
    //     boolean success = notificationService.deleteNotification(id);
    //     if (success) {
    //         return ResponseEntity.ok("Notification deleted successfully.");
    //     } else {
    //         return ResponseEntity.badRequest().body("Notification not found.");
    //     }
    // }
}
