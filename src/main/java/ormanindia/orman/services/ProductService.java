package ormanindia.orman.services;

import ormanindia.orman.models.Product;
import ormanindia.orman.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    // Create a new Product
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    // Get Product by ID
    public Optional<Product> getProductById(String id) {
        return productRepository.findById(id);
    }

    // Get all Products
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // Update Product by ID
    public Product updateProduct(String id, Product product) {
        if (productRepository.existsById(id)) {
            product.setId(id);
            return productRepository.save(product);
        }
        return null; // Return null if product does not exist
    }

    // Delete Product by ID
    public boolean deleteProduct(String id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
            return true; // Product deleted successfully
        }
        return false; // Product not found
    }
}
