package ormanindia.orman.controllers;

import ormanindia.orman.models.Product;
import ormanindia.orman.repositories.ProductRepository;
import ormanindia.orman.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController

@RequestMapping("/api/products")
public class ProductController {
    @Autowired
    private  ProductService productService;
    @Autowired
    private ProductRepository productRepository;
   

    // Create a new Product
    @PostMapping("/create")
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        Product createdProduct = productService.createProduct(product);
        return ResponseEntity.status(201).body(createdProduct); // Return CREATED status (201)
    }

    // Get Product by ID
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable String id) {
        Optional<Product> product = productService.getProductById(id);
        return product.map(ResponseEntity::ok) // Return OK (200) if found
                .orElseGet(() -> ResponseEntity.notFound().build()); // Return NOT FOUND (404) if not found
    }

    // Get all Products
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return ResponseEntity.ok(products); // Return OK (200) with list of products
    }

    // Update Product by ID
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable String id, @RequestBody Product product) {
        Product updatedProduct = productService.updateProduct(id, product);
        return updatedProduct != null ? ResponseEntity.ok(updatedProduct) // Return OK (200) if updated
                : ResponseEntity.notFound().build(); // Return NOT FOUND (404) if not found
    }

    // Delete Product by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable String id) {
        boolean deleted = productService.deleteProduct(id);
        return deleted ? ResponseEntity.noContent().build() // Return NO CONTENT (204) if deleted
                : ResponseEntity.notFound().build(); // Return NOT FOUND (404) if not found
    }
    @GetMapping("/categories")
    public List<String> getCategories() {
        // Fetch all products
        List<Product> products = productRepository.findAll();

        // Extract distinct categories using Java Streams
        return products != null ? products.stream()
                .map(Product::getCategory)       // Map each product to its category
                .filter(Objects::nonNull)        // Ensure categories are not null (optional)
                .distinct()                      // Get distinct categories
                .collect(Collectors.toList())    // Collect the results into a list
                : Collections.emptyList();       // Return empty list if products is null
    }

}
