package APITaller.example.Tienda.Repository;

import APITaller.example.Tienda.Model.Entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Product findProductById(Long productId);

    @Query("select p.value from Product p where p.id=:id")
    Double findValueById(Long id);
}