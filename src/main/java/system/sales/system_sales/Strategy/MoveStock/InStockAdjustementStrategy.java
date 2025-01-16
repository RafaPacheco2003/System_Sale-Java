package system.sales.system_sales.Strategy.MoveStock;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import system.sales.system_sales.Entity.Move;
import system.sales.system_sales.Entity.Product;

@Component
@Qualifier("IN")
public class InStockAdjustementStrategy extends StockAdjustmentStrategy {

    @Override
    public void adjust(Product product, Move move) {
        product.setStock(product.getStock() + move.getQuantity());
    }
}
