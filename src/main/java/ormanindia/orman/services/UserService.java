package ormanindia.orman.services;

import ormanindia.orman.models.User;
import ormanindia.orman.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User createUser(User user) {
        return userRepository.save(user);  // Just save the user and return it
    }

    public Optional<User> getUserById(String id) {
        return userRepository.findById(id);  // Return Optional<User> to handle "not found" in the controller
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();  // Return the list of users
    }

    public User updateUser(String id, User user) {
        if (userRepository.existsById(id)) {
            user.setId(id);
            return userRepository.save(user);  // Update and return the user
        }
        return null;  // If not found, return null
    }

    public boolean deleteUser(String id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;  // Return true if deletion is successful
        }
        return false;  // Return false if not found
    }
}
