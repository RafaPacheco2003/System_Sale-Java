package system.sales.system_sales.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import system.sales.system_sales.Strategy.MoveStock.StockAdjustmentStrategy;
import system.sales.system_sales.Strategy.MoveStock.AdjustmentStockStrategy;
import system.sales.system_sales.Strategy.MoveStock.InStockAdjustementStrategy;
import system.sales.system_sales.Strategy.MoveStock.OutStockAdjustementStrategy;

import system.sales.system_sales.Entity.MoveType;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class MoveServiceConfig {

    @Bean
    public Map<MoveType, StockAdjustmentStrategy> moveMap(
            AdjustmentStockStrategy adjustmentStockStrategy,
            InStockAdjustementStrategy inStockAdjustmentStrategy,
            OutStockAdjustementStrategy outStockAdjustmentStrategy) {

        Map<MoveType, StockAdjustmentStrategy> moveMap = new HashMap<>();
        moveMap.put(MoveType.ADJUSTMENT, adjustmentStockStrategy);
        moveMap.put(MoveType.IN, inStockAdjustmentStrategy);
        moveMap.put(MoveType.OUT, outStockAdjustmentStrategy);
        return moveMap;
    }
}
