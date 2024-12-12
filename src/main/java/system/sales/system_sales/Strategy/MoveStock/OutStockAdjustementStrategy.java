package system.sales.system_sales.Strategy.MoveStock;

import org.springframework.beans.factory.annotation.Autowired;

import system.sales.system_sales.Entity.Move;
import system.sales.system_sales.Entity.Product;
import system.sales.system_sales.Exception.DTO.InsufficientStockException;
import system.sales.system_sales.Modal.StockService;

public class OutStockAdjustementStrategy implements StockAdjustementStrategy {

    @Autowired
    private StockService stockService;

    @Override
    public void adjust(Product product, Move move) {
        
        if (product.getStock() < move.getQuantity()) {
            throw new InsufficientStockException("Stock insuficiente para el producto");

        }

        product.setStock(product.getStock() - move.getQuantity());
    }
    
}
