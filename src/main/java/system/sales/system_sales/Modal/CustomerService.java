package system.sales.system_sales.Modal;

import java.util.List;
import java.util.Optional;

import system.sales.system_sales.DTO.CustomerDTO;
import system.sales.system_sales.Entity.Customer;

public interface CustomerService {

    /*
     * CRUD
     */
    CustomerDTO createCustomerDTO(CustomerDTO customerDTO);
    Optional <CustomerDTO> getCustomerById(Long customerId);
    CustomerDTO updateCustomerDTO(Long customerId, CustomerDTO customerDTO);
    List<CustomerDTO> getAllCustomer();
    void deleteCustomer(Long customerId);

    
}
