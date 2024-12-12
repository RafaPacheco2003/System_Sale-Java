package system.sales.system_sales.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import system.sales.system_sales.Entity.PaymentMethod;
import system.sales.system_sales.Entity.Sale;

public interface SaleRepository extends JpaRepository<Sale, Long> {
   
    List<Sale> findByPaymentMethod(PaymentMethod paymentMethod);
}
