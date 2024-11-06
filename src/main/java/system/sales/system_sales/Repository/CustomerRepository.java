package system.sales.system_sales.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import system.sales.system_sales.Entity.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long>{
    
}
