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

    // Add item to cart
    public void addToCart(String restaurantId, OrderItem orderItem) {
        Restaurant restaurant = getRestaurantById(restaurantId);
        restaurant.getCaItems().add(orderItem);
        restaurantRepository.save(restaurant);
    }

    // Remove item from cart
    public void removeFromCart(String restaurantId, OrderItem orderItem) {
        Restaurant restaurant = getRestaurantById(restaurantId);
        restaurant.getCaItems().remove(orderItem);
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
