package system.sales.system_sales.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import system.sales.system_sales.Entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    
}
