package APITaller.example.Tienda.Service;

import APITaller.example.Tienda.Model.Entity.Category;
import APITaller.example.Tienda.Model.Entity.Product;
import APITaller.example.Tienda.Model.Request.ProductRequest;
import APITaller.example.Tienda.Repository.CategoryRepository;
import APITaller.example.Tienda.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Autowired
    private CategoryRepository categoryRepository;


    public Product createProduct(Product product) {




        Product savedProduct = productRepository.save(product);

        return savedProduct;
    }


    public Optional<Product> findProductById(Long productId) {
        return productRepository.findById(productId);
    }

    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }

    public Product updateProduct(Long productId, ProductRequest request) {
        Product product = productRepository.findProductById(productId);

        product.setName(request.getName());
        product.setValue(request.getPrice());

        Category category = categoryRepository.findById(request.getCategoryId());
        product.setCategory(category);

        return productRepository.save(product);
    }

    public void deleteProduct(Long productId) {
        Product existingProduct = productRepository.findProductById(productId);

    }

}
