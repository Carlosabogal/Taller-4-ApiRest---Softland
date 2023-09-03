package APITaller.example.Tienda.Service;

import APITaller.example.Tienda.Model.Entity.Customer;
import APITaller.example.Tienda.Repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {


    private CustomerRepository customerRepository;
    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer saveCustomer(Customer customer) {
        try {
            return customerRepository.save(customer);
        } catch (Exception e) {
            throw new RuntimeException("Error in the application: " + e.getMessage());
        }
    }
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }
    public ResponseEntity<Customer> update(Long id, Customer updatedCustomer) {
        try {
            Optional<Customer> optionalExistingCustomer = customerRepository.findById(id);

            if (optionalExistingCustomer.isPresent()) {
                Customer existingCustomer = optionalExistingCustomer.get();
                existingCustomer.setName(updatedCustomer.getName());
                existingCustomer.setEmail(updatedCustomer.getEmail());
                existingCustomer.setPassword(updatedCustomer.getPassword());

                Customer savedCustomer = customerRepository.save(existingCustomer);
                return ResponseEntity.ok(savedCustomer);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    public Optional<Customer> getCustomerById(Long id) {
        return customerRepository.findById(id);
    }

    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }
}
