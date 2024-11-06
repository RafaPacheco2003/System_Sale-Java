package system.sales.system_sales.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import system.sales.system_sales.Entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    
}