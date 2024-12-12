package system.sales.system_sales.Modal;

import java.util.List;
import java.util.Optional;

import system.sales.system_sales.DTO.SaleDTO;

public interface SaleService {
    SaleDTO createSaleDTO(SaleDTO saleDTO);
    Optional<SaleDTO> getByIdSale(Long id_sale);
    List<SaleDTO> getAllSale();
    List<SaleDTO> getSaleByPaymentMethod(String paymentMethod);

    Integer getIdUserFromToken(String token);
    
}
