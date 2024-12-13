package ormanindia.orman.controllers;

import ormanindia.orman.models.User;
import ormanindia.orman.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Create a new User
    @PostMapping("/register")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User createdUser = userService.createUser(user);  // Call service to create a user
        return ResponseEntity.status(201).body(createdUser);  // Return CREATED status (201)
    }

    // Get User by ID
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable String id) {
        Optional<User> user = userService.getUserById(id);
        return user.map(ResponseEntity::ok)  // Return OK (200) if found
                .orElseGet(() -> ResponseEntity.notFound().build());  // Return NOT FOUND (404) if not found
    }

    // Get all Users
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();  // Call service to get all users
        return ResponseEntity.ok(users);  // Return OK (200) with list of users
    }

    // Update User by ID
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable String id, @RequestBody User user) {
        User updatedUser = userService.updateUser(id, user);
        return updatedUser != null ? ResponseEntity.ok(updatedUser)  // Return OK (200) if updated
                : ResponseEntity.notFound().build();  // Return NOT FOUND (404) if not found
    }

    // Delete User by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable String id) {
        boolean deleted = userService.deleteUser(id);
        return deleted ? ResponseEntity.noContent().build()  // Return NO CONTENT (204) if deleted
                : ResponseEntity.notFound().build();  // Return NOT FOUND (404) if not found
    }
}
