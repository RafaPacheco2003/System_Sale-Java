package system.sales.system_sales.Modal;

import system.sales.system_sales.DTO.ProductDTO;
import system.sales.system_sales.Entity.Product;
import system.sales.system_sales.Exception.DTO.ProductNotFoundException;

public interface StockService {
    void validateStock(Product product, int quantity) throws ProductNotFoundException;

}
