package ormanindia.orman.controllers;

import ormanindia.orman.models.OrderItem;
import ormanindia.orman.models.Product;
import ormanindia.orman.models.Restaurant;
import ormanindia.orman.services.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/restaurants")
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    // Create a new restaurant
    @PostMapping
    public ResponseEntity<Restaurant> createRestaurant(@RequestBody Restaurant restaurant) {
        Restaurant createdRestaurant = restaurantService.createRestaurant(restaurant);
        return ResponseEntity.ok(createdRestaurant);
    }

    // Get a restaurant by ID
    @GetMapping("/{id}")
    public ResponseEntity<Restaurant> getRestaurantById(@PathVariable String id) {
        Restaurant restaurant = restaurantService.getRestaurantById(id);
        if (restaurant == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(restaurant);
    }

    // Get all restaurants
    @GetMapping
    public ResponseEntity<List<Restaurant>> getAllRestaurants() {
        List<Restaurant> restaurants = restaurantService.getAllRestaurants();
        return ResponseEntity.ok(restaurants);
    }

    // Update a restaurant
    @PutMapping("/{id}")
    public ResponseEntity<Restaurant> updateRestaurant(@PathVariable String id, @RequestBody Restaurant restaurant) {
        Restaurant updatedRestaurant = restaurantService.updateRestaurant(restaurant, id);
        if (updatedRestaurant == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedRestaurant);
    }

    // Delete a restaurant
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRestaurant(@PathVariable String id) {
        boolean isDeleted = restaurantService.deleteRestaurant(id);
        if (!isDeleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

    // Add a product to the favorite list
    @PostMapping("/{id}/favorites")
    public ResponseEntity<Void> addToFavoriteList(@PathVariable String id, @RequestBody Product product) {
        restaurantService.addToFavList(id, product);
        return ResponseEntity.ok().build();
    }
  
    

    // Remove a product from the favorite list
    @DeleteMapping("/{id}/favorites")
    public ResponseEntity<Void> removeFromFavoriteList(@PathVariable String id, @RequestBody Product product) {
        restaurantService.removeFromFavList(id, product);
        return ResponseEntity.ok().build();
    }

    // Get the favorite list
    @GetMapping("/{id}/favorites")
    public ResponseEntity<List<Product>> getFavoriteList(@PathVariable String id) {
        List<Product> favoriteList = restaurantService.getFavList(id);
        return ResponseEntity.ok(favoriteList);
    }
   
    // Add an item to the cart
    @PostMapping("/{id}/cart")
    public ResponseEntity<Void> addToCart(@PathVariable String id, @RequestBody OrderItem orderItem) {
        restaurantService.addToCart(id, orderItem);
        return ResponseEntity.ok().build();
    }

    // Remove an item from the cart
   @DeleteMapping("/{restaurantId}/{productId}")
public ResponseEntity<Void> removeFromCart(
        @PathVariable String restaurantId,
        @PathVariable String productId) {
    restaurantService.removeFromCart(restaurantId, productId);
    return ResponseEntity.ok().build();
}


    // Get cart items
    @GetMapping("/{id}/cart")
    public ResponseEntity<List<OrderItem>> getCartItems(@PathVariable String id) {
        List<OrderItem> cartItems = restaurantService.getCartItems(id);
        return ResponseEntity.ok(cartItems);
    }
}
