package system.sales.system_sales.Repository;


import org.springframework.data.jpa.repository.JpaRepository;

import system.sales.system_sales.Entity.Supplier;

public interface SupplierRepository extends JpaRepository<Supplier, Long>{
    
}
