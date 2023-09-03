package APITaller.example.Tienda.Repository;

import APITaller.example.Tienda.Model.Entity.Discount;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DiscountRepository extends JpaRepository<Discount, Long> {

    @Query("select p.percentageDiscount from Discount p where  p.id=1")
    int findPercentageDiscountById();
}
