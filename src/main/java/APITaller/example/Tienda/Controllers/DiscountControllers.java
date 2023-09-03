package APITaller.example.Tienda.Controllers;

import APITaller.example.Tienda.Model.Entity.Discount;
import APITaller.example.Tienda.Service.DiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/discounts")
public class DiscountControllers {

    private DiscountService discountService;

    @Autowired
    public DiscountControllers(DiscountService discountService) {
        this.discountService = discountService;
    }

    @PostMapping("/add")
    public ResponseEntity<Discount> createDiscountCompany(
            @RequestBody Discount discount
    ) {
        Discount createdDiscount = discountService.createDiscountCompany(discount.getPercentageDiscount());
        return new ResponseEntity<>(createdDiscount, HttpStatus.CREATED);
    }
}