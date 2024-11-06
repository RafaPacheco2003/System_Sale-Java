package system.sales.system_sales.Controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import system.sales.system_sales.DTO.CustomerDTO;
import system.sales.system_sales.Exception.DTO.CustomerNotFoundException;
import system.sales.system_sales.Modal.CustomerService;

@RestController
@RequestMapping("/admin/customer")
public class CustomerController {
    
    @Autowired
    private CustomerService customerService;

    @PostMapping("/create")
    public ResponseEntity<?> createCustomer(@RequestBody CustomerDTO customerDTO){
        CustomerDTO createCustomer= customerService.createCustomerDTO(customerDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("Cliente creado "+ createCustomer.getFirstName());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable Long id){
        return customerService.getCustomerById(id)
                .map(dto -> ResponseEntity.ok(dto))
                .orElseThrow(() -> new CustomerNotFoundException("Cliente no encontrado"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateCustomer(@PathVariable Long id, @RequestBody CustomerDTO customerDTO){
        customerService.updateCustomerDTO(id, customerDTO);
        return ResponseEntity.ok("Cliente actualizado correctamente con ID: " + id);
    }


    @GetMapping("/all")
    public ResponseEntity<List<CustomerDTO>> getAllCustomer(){
        List<CustomerDTO> customersDTO= customerService.getAllCustomer();

        return ResponseEntity.ok(customersDTO);
    }
}
