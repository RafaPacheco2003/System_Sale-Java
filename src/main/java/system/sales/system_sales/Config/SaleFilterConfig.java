package system.sales.system_sales.Config;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import system.sales.system_sales.Filter.FilterSale.DateFilter;
import system.sales.system_sales.Filter.FilterSale.PaymentMethodFilter;
import system.sales.system_sales.Filter.FilterSale.SaleFilter;

@Configuration
public class SaleFilterConfig {
    
    @Bean
    public List<SaleFilter> filters(){
        return Arrays.asList(new DateFilter(), new PaymentMethodFilter());
    }
}
