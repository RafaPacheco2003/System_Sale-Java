package system.sales.system_sales.Strategy.MoveStock;

import system.sales.system_sales.Entity.Move;
import system.sales.system_sales.Entity.Product;

public abstract class StockAdjustmentStrategy {

    public abstract void adjust(Product product, Move move);

}
