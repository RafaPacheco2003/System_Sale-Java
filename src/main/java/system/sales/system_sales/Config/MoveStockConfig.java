package system.sales.system_sales.Config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import system.sales.system_sales.Entity.MoveType;
import system.sales.system_sales.Strategy.MoveStock.AdjustmentStockStrategy;
import system.sales.system_sales.Strategy.MoveStock.InStockAdjustementStrategy;
import system.sales.system_sales.Strategy.MoveStock.OutStockAdjustementStrategy;
import system.sales.system_sales.Strategy.MoveStock.StockAdjustementStrategy;

@Configuration
public class MoveStockConfig {

    @Bean
    public Map<MoveType, StockAdjustementStrategy> moveStockAdjustmentStrategies() {
        Map<MoveType, StockAdjustementStrategy> map = new HashMap<>();
        map.put(MoveType.IN, new InStockAdjustementStrategy());
        map.put(MoveType.OUT, new OutStockAdjustementStrategy());
        map.put(MoveType.ADJUSTMENT, new AdjustmentStockStrategy());
        return map;
    }
}
