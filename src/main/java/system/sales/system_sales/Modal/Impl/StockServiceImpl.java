package system.sales.system_sales.Modal.Impl;

import org.springframework.stereotype.Service;

import system.sales.system_sales.DTO.ProductDTO;
import system.sales.system_sales.Entity.Product;
import system.sales.system_sales.Exception.DTO.ProductNotFoundException;
import system.sales.system_sales.Modal.StockService;

@Service
public class StockServiceImpl implements StockService {

    @Override
    public void validateStock(Product product, int quantity) throws ProductNotFoundException {
        // Validar si el stock es suficiente
        if (product.getStock() < quantity) {
            throw new ProductNotFoundException("Stock insuficiente para el producto: " + product.getName());
        }
    }

}
