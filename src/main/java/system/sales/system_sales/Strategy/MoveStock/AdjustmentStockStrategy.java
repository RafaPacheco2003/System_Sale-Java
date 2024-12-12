package system.sales.system_sales.Strategy.MoveStock;

import system.sales.system_sales.Entity.Move;
import system.sales.system_sales.Entity.Product;

public class AdjustmentStockStrategy implements StockAdjustementStrategy {

    @Override
    public void adjust(Product product, Move move) {
      product.setStock(move.getQuantity());
    }
    
}
