package ormanindia.orman.services;

import ormanindia.orman.models.Product;
import ormanindia.orman.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    // Create a new product
    public Product createProduct(Product product) {
       
        if (product.isCategoryPriceConstant()) {
            // Ensure all products in this category have the same price
            List<Product> categoryProducts = productRepository.findAll()
                    .stream()
                    .filter(p -> p.getCategory().equals(product.getCategory()))
                    .collect(Collectors.toList());

            if (!categoryProducts.isEmpty()) {
                product.setPrice(categoryProducts.get(0).getPrice());
            }
        }
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

    // Get all Products by Category
    public List<Product> getProductsByCategory(String category) {
        return productRepository.findAll()
                .stream()
                .filter(product -> product.getCategory().equals(category))
                .collect(Collectors.toList());
    }

    // Update Product by ID
    public Product updateProduct(String id, Product product) {
        Product existingProduct = productRepository.findById(id).orElse(null);

        if (existingProduct != null) {
            // Update fields
            existingProduct.setName(product.getName() != null ? product.getName() : existingProduct.getName());
            existingProduct.setCategory(product.getCategory() != null ? product.getCategory() : existingProduct.getCategory());
            existingProduct.setDescription(product.getDescription() != null ? product.getDescription() : existingProduct.getDescription());
            existingProduct.setImage(product.getImage() != null ? product.getImage() : existingProduct.getImage());
            existingProduct.setAvailable(product.isAvailable());
            existingProduct.setUnit(product.getUnit()!=null?product.getUnit() : existingProduct.getUnit());
            existingProduct.setCategoryPriceConstant(product.isCategoryPriceConstant());

            // Handle pricing logic
            if (product.isCategoryPriceConstant()) {
                List<Product> categoryProducts = getProductsByCategory(product.getCategory());
                double constantPrice = product.getPrice();
                categoryProducts.forEach(p -> {
                    p.setPrice(constantPrice);
                    productRepository.save(p);
                });
                existingProduct.setPrice(constantPrice);
            } else {
                existingProduct.setPrice(product.getPrice());
            }

            return productRepository.save(existingProduct);
        }
        return null; // Return null if product does not exist
    }

    // Delete Product by ID
    public boolean deleteProduct(String id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
            return true;
        }
        return false; // Product not found
    }
}
