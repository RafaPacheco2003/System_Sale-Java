package system.sales.system_sales.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import system.sales.system_sales.Entity.DetailsSale;

public interface DetailsRepository extends JpaRepository<DetailsSale, Long>{
    
}
