package system.sales.system_sales.Modal;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import system.sales.system_sales.DTO.SaleDTO;
import system.sales.system_sales.Response.SalesResponse;

public interface SaleService {
    SaleDTO createSaleDTO(SaleDTO saleDTO);
    Optional<SaleDTO> getByIdSale(Long id_sale);
    List<SaleDTO> getAllSale();
    List<SaleDTO> getSaleByPaymentMethod(String paymentMethod);
    List<SaleDTO> getSaleByDate(Date date);
    SalesResponse getSalesAndTotalByDate(Date date);
    

    /*
     * Filter
     */
    SalesResponse getSalesTotalAmountByFilter(Date date, String paymentMethod);

    
    Integer getIdUserFromToken(String token);
    
}
