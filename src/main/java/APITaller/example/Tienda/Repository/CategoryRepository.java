package APITaller.example.Tienda.Repository;

import APITaller.example.Tienda.Model.Entity.Category;
import APITaller.example.Tienda.Model.Request.CategoryRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    Category save(Category category);

    CategoryRequest save(CategoryRequest category);

    Category findById(int categoryId);
}