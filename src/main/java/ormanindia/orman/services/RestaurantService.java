package ormanindia.orman.services;

import ormanindia.orman.models.OrderItem;
import ormanindia.orman.models.Product;
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


    public Restaurant getRestaurantById(String id) {
        return restaurantRepository.findByid(id);
    }

    public List<Restaurant> getAllRestaurants() {
        return restaurantRepository.findAll();
    }

    public Restaurant updateRestaurant(Restaurant restaurant, String id) {
        // Find the existing restaurant
        Restaurant existingRestaurant = restaurantRepository.findById(id).orElse(null);
    
        if (existingRestaurant != null) {
            // Find the corresponding User entity
            User user = userService.findByusername(existingRestaurant.getEmail());
    
            // Update the restaurant fields
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
    
            // Check if email is updated and update in both Restaurant and User
            if (restaurant.getEmail() != null && !restaurant.getEmail().isEmpty()
                    && !restaurant.getEmail().equals(existingRestaurant.getEmail())) {
                existingRestaurant.setEmail(restaurant.getEmail());
                if (user != null) {
                    user.setUsername(restaurant.getEmail());
                }
            }
    
            // Check if password is updated and update in both Restaurant and User
            if (restaurant.getPassword() != null && !restaurant.getPassword().isEmpty()
                    && !restaurant.getPassword().equals(existingRestaurant.getPassword())) {
                existingRestaurant.setPassword(restaurant.getPassword());
                if (user != null) {
                    user.setPassword(restaurant.getPassword()); // Ensure the password is hashed before saving
                }
            }
    
            // Update additional fields
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
    
            // Save the updated User entity
            if (user != null) {
                userService.saveUser(user); // Save the updated User (use your User service's save method)
            }
    
            // Save the updated Restaurant entity
            return restaurantRepository.save(existingRestaurant);
        } else {
            throw new IllegalArgumentException("Restaurant not found with ID: " + id);
        }
    }
    
 public void addToFavList(String restaurantId, Product product) {
        Restaurant restaurant = getRestaurantById(restaurantId);
        restaurant.getFavList().add(product);
        restaurantRepository.save(restaurant);
    }

    // Remove item from favorite list
    public void removeFromFavList(String restaurantId, Product product) {
        Restaurant restaurant = getRestaurantById(restaurantId);
        restaurant.getFavList().remove(product);
        restaurantRepository.save(restaurant);
    }

    // Get favorite list
    public List<Product> getFavList(String restaurantId) {
        Restaurant restaurant = getRestaurantById(restaurantId);
        return restaurant.getFavList();
    }

    //Daily Scheduled Order
    public List<OrderItem> getdailyProducts(String restaurantId) {
        Restaurant restaurant = getRestaurantById(restaurantId);
        return restaurant.getDailyScheduledItems();
    }
    public void addToDailyScheduledItems(String restaurantId, OrderItem orderItem) {
        Restaurant restaurant = getRestaurantById(restaurantId);
        restaurant.getDailyScheduledItems().add(orderItem);
        restaurantRepository.save(restaurant);
    }
    
    public void removeFromDailyScheduledItems(String restaurantId, String productId) {
        // Find the restaurant by its ID
        Restaurant restaurant = getRestaurantById(restaurantId);

        // Filter out the OrderItem with the matching product ID
        restaurant.setDailyScheduledItems(
            restaurant.getDailyScheduledItems().stream()
                .filter(orderItem -> !orderItem.getProduct().getId().equals(productId))
                .collect(Collectors.toList())
        );

        // Save the updated restaurant
        restaurantRepository.save(restaurant);
    }
// Add item to cart
public void addToCart(String restaurantId, OrderItem orderItem) {
    Restaurant restaurant = getRestaurantById(restaurantId);
    restaurant.getCaItems().add(orderItem);
    restaurantRepository.save(restaurant);
}
    // Remove item from cart
    public void removeFromCart(String restaurantId, String productId) {
        // Find the restaurant by its ID
        Restaurant restaurant = getRestaurantById(restaurantId);

        // Filter out the OrderItem with the matching product ID
        restaurant.setCaItems(
            restaurant.getCaItems().stream()
                .filter(orderItem -> !orderItem.getProduct().getId().equals(productId))
                .collect(Collectors.toList())
        );

        // Save the updated restaurant
        restaurantRepository.save(restaurant);
    }

    // Get cart items
    public List<OrderItem> getCartItems(String restaurantId) {
        Restaurant restaurant = getRestaurantById(restaurantId);
        return restaurant.getCaItems();
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
