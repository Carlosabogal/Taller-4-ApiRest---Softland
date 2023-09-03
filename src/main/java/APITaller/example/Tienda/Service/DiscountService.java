package APITaller.example.Tienda.Service;

import APITaller.example.Tienda.Model.Entity.Discount;
import APITaller.example.Tienda.Repository.DiscountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DiscountService {

    private final DiscountRepository discountRepository;

    @Autowired
    public DiscountService(DiscountRepository discountRepository) {
        this.discountRepository = discountRepository;
    }

    public Discount createDiscountCompany(int percentageDiscount) {
        Discount discount = new Discount(1L, percentageDiscount);
        return discountRepository.save(discount);
    }
}