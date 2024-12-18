package ormanindia.orman.controllers;

import ormanindia.orman.models.User;
import ormanindia.orman.models.Product;
import ormanindia.orman.services.UserService;
import ormanindia.orman.services.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/public") // Base path for public endpoints
public class PublicController {

    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ProductService productService;

    // POST: /public/register
    @PostMapping("/register")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User createdUser = userService.createUser(user);  // Call service to create a user
        return ResponseEntity.status(201).body(createdUser);  // Return CREATED status (201)
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User loginRequest) {
        // Fetch user by username (assuming username is unique)
        User storedUser = userService.findByusername(loginRequest.getUsername());
    
        if (storedUser == null) {
            return ResponseEntity.status(404).body("User not found");
        }
    
        // Compare the raw password with the encoded password in the database
        boolean passwordMatches = passwordEncoder.matches(loginRequest.getPassword(), storedUser.getPassword());
    
        if (!passwordMatches) {
            return ResponseEntity.status(401).body("Invalid password");
        }
    
        return ResponseEntity.ok(storedUser);
    }
    
    

    // GET: /public/products
    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.getAllProducts(); // Fetch all products
        return ResponseEntity.ok(products); // Return OK (200) with list of products
    }
}
