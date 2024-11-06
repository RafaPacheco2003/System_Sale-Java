package system.sales.system_sales.Modal.Impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import system.sales.system_sales.DTO.CustomerDTO;
import system.sales.system_sales.Entity.Customer;
import system.sales.system_sales.Exception.DTO.CustomerNotFoundException;
import system.sales.system_sales.Modal.CustomerService;
import system.sales.system_sales.Repository.CustomerRepository;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;


    @Autowired
    private ModelMapper modelMapper;


    @Override
    public CustomerDTO createCustomerDTO(CustomerDTO customerDTO) {
       Customer customer= modelMapper.map(customerDTO, Customer.class);
       Customer saveCustomer= customerRepository.save(customer);
       return modelMapper.map(saveCustomer, CustomerDTO.class);
    }

    @Override
    public Optional<CustomerDTO> getCustomerById(Long customerId) {
        return customerRepository.findById(customerId)
                .map(customer -> modelMapper.map(customer, CustomerDTO.class));
    }

    @Override
    public CustomerDTO updateCustomerDTO(Long customerId, CustomerDTO customerDTO) {
       //Search existing customer by id
        Customer existingCustomer= customerRepository.findById(customerId)
                .orElseThrow(()-> new CustomerNotFoundException("Cliente no encontrado"));

        modelMapper.typeMap(CustomerDTO.class, Customer.class)
                    .addMappings(mapper -> mapper.skip(Customer::setId_customer));

        //Map the DTO to the existing entity without modifying the id
        modelMapper.map(customerDTO, existingCustomer);

        Customer updateCustomer= customerRepository.save(existingCustomer);

        return modelMapper.map(updateCustomer, CustomerDTO.class);
    }

    @Override
    public List<CustomerDTO> getAllCustomer() {
        List<Customer> customers= customerRepository.findAll();

        if(customers.isEmpty()){
            throw new CustomerNotFoundException("No hay clientes disponibles");
        }

        return customers.stream()
                .map(customer -> modelMapper.map(customer, CustomerDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteCustomer(Long customerId) {
        if(!customerRepository.existsById(customerId)){
            throw new CustomerNotFoundException("Cliente no encontrado");
        }

        customerRepository.deleteById(customerId);
    }
    
}
