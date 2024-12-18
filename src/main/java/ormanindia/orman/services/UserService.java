package ormanindia.orman.services;

import ormanindia.orman.models.User;
import ormanindia.orman.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    public User saveUser(User user){
        return userRepository.save(user);

    }

    public User findByusername(String username){
        return userRepository.findByusername(username);

    }
    public User createUser(User user) {
        user.setRole("USER");
        return userRepository.save(user);
    }

    public Optional<User> getUserById(String id) {
        return userRepository.findById(id);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User updateUser(String id, User user) {
        User existingUser = userRepository.findById(id).orElse(null);
        if (existingUser == null) {
            return null;
        }
        if (user.getUsername() != null && !user.getUsername().isEmpty()) {
            existingUser.setUsername(user.getUsername());
        }
        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            existingUser.setPassword(user.getPassword());
        }
        if (user.getRole() != null && !user.getRole().isEmpty()) {
            existingUser.setRole(user.getRole());
        }
        if (user.getRestaurants() != null && !user.getRestaurants().isEmpty()) {
            existingUser.setRestaurants(user.getRestaurants());
        }
        return userRepository.save(existingUser);
    }


    public boolean deleteUser(String id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public User findUserByRestaurantId(String restaurantId) {
        return userRepository.findAll().stream()
                .filter(user -> user.getRestaurants().stream()
                        .anyMatch(restaurant -> restaurant.getId().equals(restaurantId)))
                .findFirst()
                .orElse(null);
    }
}
