package system.sales.system_sales.Strategy.MoveStock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import system.sales.system_sales.Entity.Move;
import system.sales.system_sales.Entity.Product;
import system.sales.system_sales.Exception.DTO.InsufficientStockException;
import system.sales.system_sales.Modal.StockService;

@Component
@Qualifier("OUT")
public class OutStockAdjustementStrategy extends StockAdjustmentStrategy {

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
