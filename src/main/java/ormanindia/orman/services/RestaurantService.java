package ormanindia.orman.services;

import ormanindia.orman.models.Restaurant;
import ormanindia.orman.models.User;
import ormanindia.orman.repositories.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;
    @Autowired
    private UserService userService;

    public Restaurant createRestaurant(Restaurant restaurant) {
        User user = new User();
        user.setUsername(restaurant.getEmail());
        user.setPassword(restaurant.getPassword());
        user.setRole("USER");
        Restaurant savedRestaurant = restaurantRepository.save(restaurant);
        user.getRestaurants().add(savedRestaurant);
        userService.createUser(user);
        return savedRestaurant;
    }


    public Optional<Restaurant> getRestaurantById(String id) {
        return restaurantRepository.findById(id);
    }

    public List<Restaurant> getAllRestaurants() {
        return restaurantRepository.findAll();
    }

    public Restaurant updateRestaurant(Restaurant restaurant, String id) {
        Restaurant existingRestaurant = restaurantRepository.findById(id).orElse(null);
        if (existingRestaurant != null) {
            existingRestaurant.setRestaurantName(
                    restaurant.getRestaurantName() != null && !restaurant.getRestaurantName().isEmpty()
                            ? restaurant.getRestaurantName()
                            : existingRestaurant.getRestaurantName()
            );
            existingRestaurant.setRestaurantAddress(
                    restaurant.getRestaurantAddress() != null && !restaurant.getRestaurantAddress().isEmpty()
                            ? restaurant.getRestaurantAddress()
                            : existingRestaurant.getRestaurantAddress()
            );
            existingRestaurant.setContactNumber(
                    restaurant.getContactNumber() != null && !restaurant.getContactNumber().isEmpty()
                            ? restaurant.getContactNumber()
                            : existingRestaurant.getContactNumber()
            );
            existingRestaurant.setEmail(
                    restaurant.getEmail() != null && !restaurant.getEmail().isEmpty()
                            ? restaurant.getEmail()
                            : existingRestaurant.getEmail()
            );
            existingRestaurant.setPassword(
                    restaurant.getPassword() != null && !restaurant.getPassword().isEmpty()
                            ? restaurant.getPassword()
                            : existingRestaurant.getPassword()
            );
            existingRestaurant.setIsActive(
                    restaurant.getIsActive() != null
                            ? restaurant.getIsActive()
                            : existingRestaurant.getIsActive()
            );
            existingRestaurant.setPayment(
                    restaurant.getPayment() != null
                            ? restaurant.getPayment()
                            : existingRestaurant.getPayment()
            );
        }
        return restaurantRepository.save(existingRestaurant);
    }

    public boolean deleteRestaurant(String id) {
        // Check if the restaurant exists
        Restaurant restaurant = restaurantRepository.findById(id).orElse(null);
        if (restaurant == null) {
            return false;
        }
        User user = userService.findUserByRestaurantId(id);

        if (user != null) {
            if (user.getRestaurants().size() > 1) {
                user.setRestaurants(
                        user.getRestaurants().stream()
                                .filter(r -> !r.getId().equals(id))
                                .collect(Collectors.toList())
                );
                userService.updateUser(user.getId(), user);
            } else {
                userService.deleteUser(user.getId());
            }
        }
        restaurantRepository.deleteById(id);

        return true;
    }

}
