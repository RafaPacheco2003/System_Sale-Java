package system.sales.system_sales.Modal;

import java.util.List;

import system.sales.system_sales.DTO.DetailsSaleDTO;
import system.sales.system_sales.Entity.Product;

public interface FinancialCalculationService {
    double calculateTotalSaleAmount(List<DetailsSaleDTO> detailsSales);

}
