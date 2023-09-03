package APITaller.example.Tienda.Model.Entity;

import jakarta.persistence.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Entity
@Table(name = "Discount")
public class Discount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Min(value = 0, message = "Percentage discount cannot be negative")
    @Max(value = 100, message = "Percentage discount cannot be greater than 100")
    private int percentageDiscount ;

    public Discount() {
    }

    public Discount(Long id, int percentageDiscount) {
        this.id = id;
        this.percentageDiscount = percentageDiscount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getPercentageDiscount() {
        return percentageDiscount;
    }

    public void setPercentageDiscount(int percentageDiscount) {
        this.percentageDiscount = percentageDiscount;
    }
}