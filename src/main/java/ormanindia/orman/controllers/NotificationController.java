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

    // Create a new notification
    @PostMapping
    public ResponseEntity<Notification> createNotification(@RequestBody Notification notification) {
        notification=notificationService.createNotification(notification);
        return ResponseEntity.ok(notification);
    }

    // Get all notifications
    @GetMapping("/all")
    public ResponseEntity<List<Notification>> getAllNotifications() {
        List<Notification> notifications = notificationService.getAllNotifications();
        return ResponseEntity.ok(notifications);
    }

    // Get notifications by recipientId
    @GetMapping("/user/{recipientId}")
    public ResponseEntity<List<Notification>> getNotificationsForUser(@PathVariable String recipientId) {
        List<Notification> notifications = notificationService.getNotificationsForUser(recipientId);
        return ResponseEntity.ok(notifications);
    }

    // Get notifications with null recipientId
    // @GetMapping("/unassigned")
    // public ResponseEntity<List<Notification>> getUnassignedNotifications() {
    //     List<Notification> notifications = notificationService.getUnassignedNotifications();
    //     return ResponseEntity.ok(notifications);
    // }
}
