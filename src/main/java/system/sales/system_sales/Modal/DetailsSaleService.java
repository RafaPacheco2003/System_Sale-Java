package system.sales.system_sales.Modal;

import java.util.List;

import system.sales.system_sales.DTO.DetailsSaleDTO;
import system.sales.system_sales.Entity.Sale;

public interface DetailsSaleService {
    void addDetailsToSale(Sale sale, List<DetailsSaleDTO> detailsSales);
}
