package APITaller.example.Tienda.Service;

import APITaller.example.Tienda.Model.Entity.Customer;
import APITaller.example.Tienda.Model.Entity.Sale;
import APITaller.example.Tienda.Repository.DiscountRepository;
import APITaller.example.Tienda.Repository.ProductRepository;
import APITaller.example.Tienda.Repository.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SaleService {
    @Autowired
    private SaleRepository saleRepository;
    @Autowired
    private DiscountRepository discountRepository;
    @Autowired
    private ProductRepository productRepository;


    public Optional<Sale> findById(Long id) throws Exception{
        try{
            return saleRepository.findById(id);

        }catch (Exception e){
            throw new Exception("An Error ocurred: " + e.getMessage());
        }

    }

    public List<Sale> findAll() {
        return saleRepository.findAll();
    }


    public Sale save(Sale sale) throws Exception {
        try {
            if (sale.getQuantity() == null) {
                throw new Exception("Quantity must be provided for the sale.");
            }

            double price = sale.getQuantity() * productRepository.findValueById(sale.getProduct().getId());
            double invoicePrice = asignDiscount(price, sale);

            sale.setTotalPrice((long) invoicePrice);

            return saleRepository.save(sale);

        } catch (Exception e) {
            throw new Exception("An Error occurred while saving the sale: " + e.getMessage());
        }
    }

    public double assignDiscountCompany(double totalAmount, Sale sale) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(sale.getDate());
        calendar.add(Calendar.DAY_OF_MONTH, -31);
        Date saleDate = calendar.getTime();

        List<Sale> sales = saleRepository.findByCustomerAndDateBetween(saleDate, sale.getDate(), sale.getCustomer());

        double totalSalesAmount = 0.0;

        for (Sale saleItem : sales) {
            totalSalesAmount += saleItem.getTotalPrice();
        }

        if (!sales.isEmpty() && totalSalesAmount > 1000000) {
            int discountPercentage = discountRepository.findPercentageDiscountById();

            double discountFactor = discountPercentage / 100.0;

            totalAmount = totalAmount * (1 - discountFactor);
        }
        return totalAmount;
    }
        public double asignDiscount(double totalAmount,Sale sale) {

             int maxAttempts = 3;


             Random random = new Random();

            int attempts = 0;
            int discountPercentage = discountRepository.findPercentageDiscountById();

            while (attempts < maxAttempts) {
                int option = random.nextInt(3);

                switch (option) {
                    case 0:
                        return totalAmount-(totalAmount*discountPercentage)/100;
                    case 1:
                        attempts++;
                        continue;
                    case 2:
                        return totalAmount;
                }
            }

            return totalAmount;
        }


    public void deleteById(Long id) {
        saleRepository.deleteById(id);
    }

    public List<Sale> obtenerVentasPorRangoDeFechasYCliente(Date fechaInicio, Date fechaFin, Customer cliente) {
        return saleRepository.findByCustomerAndDateBetween(fechaInicio, fechaFin, cliente);
    }

}