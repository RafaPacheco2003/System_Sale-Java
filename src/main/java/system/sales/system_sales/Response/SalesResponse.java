package system.sales.system_sales.Response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import system.sales.system_sales.DTO.SaleDTO;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SalesResponse {
    private List<SaleDTO> sales;
    private double totalSalesAmount;

}
