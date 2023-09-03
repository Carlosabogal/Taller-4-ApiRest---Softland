package APITaller.example.Tienda.Controllers;

import APITaller.example.Tienda.Model.Entity.Customer;
import APITaller.example.Tienda.Model.Entity.Sale;
import APITaller.example.Tienda.Repository.CustomerRepository;
import APITaller.example.Tienda.Repository.SaleRepository;
import APITaller.example.Tienda.Service.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/sales")
public class SaleControllers {

    @Autowired
    private SaleService saleService;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private SaleRepository saleRepository;

    public SaleControllers(SaleService saleService, CustomerRepository customerRepository, SaleRepository saleRepository) {
        this.saleService = saleService;
        this.customerRepository = customerRepository;
        this.saleRepository = saleRepository;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getSaleById(@PathVariable Long id) {
        try {
            Optional<Sale> sale = saleService.findById(id);
            if (sale != null) {
                return new ResponseEntity<>(sale, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Sale not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<Object> getAllSales() {
        try {
            List<Sale> sales = saleService.findAll();
            return new ResponseEntity<>(sales, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/save")
    public ResponseEntity<Object> saveSale(@RequestBody Sale sale) throws Exception {
        try {
            saleService.save(sale);
            return new ResponseEntity<>("Sale was done correctly", HttpStatus.OK);
        } catch (Exception e) {
            throw new Exception("An Error occurred while saving the sale: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteSale(@PathVariable Long id) {
        try {
            saleService.deleteById(id);
            return new ResponseEntity<>("Sale was deleted correctly", HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/customer/{customerId}/date-range")
    public ResponseEntity<List<Sale>> getSalesByDateRangeAndCustomer(
            @PathVariable Long customerId,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
        Optional<Customer> customer = customerRepository.findById(customerId);
        if (customer.isPresent()) {
            List<Sale> sales = saleService.obtenerVentasPorRangoDeFechasYCliente(startDate, endDate, customer.get());
            return ResponseEntity.ok(sales);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/apply-discount/{saleId}")
    public ResponseEntity<String> applyDiscount(@PathVariable Long saleId) {
        try {
            Optional<Sale> saleOptional = saleRepository.findById(saleId);
            if (saleOptional.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            Sale sale = saleOptional.get();
            double totalAmount = sale.getTotalPrice();
            double discountedTotalAmount = saleService.assignDiscountCompany(totalAmount, sale);

            sale.setTotalPrice((long) discountedTotalAmount);
            saleRepository.save(sale);

            return ResponseEntity.ok("Sale with ID " + saleId + " updated with discounted total amount: " + discountedTotalAmount);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error applying discount: " + e.getMessage());
        }
    }
}