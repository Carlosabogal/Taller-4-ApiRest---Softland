package APITaller.example.Tienda.Repository;

import APITaller.example.Tienda.Model.Entity.Customer;
import APITaller.example.Tienda.Model.Entity.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {

    @Query("SELECT v FROM Sale v WHERE v.date BETWEEN :dateStart AND :dateEnd AND v.customer = :cliente")
    List<Sale> findByCustomerAndDateBetween(Date dateStart, Date dateEnd, Customer cliente);

}
