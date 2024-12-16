package system.sales.system_sales.Filter.FilterSale;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import system.sales.system_sales.Entity.Sale;

public class DateFilter extends SaleFilter{

    @Override
    public List<Sale> filter(List<Sale> sales, Date date, String paymentMethod) {
        if (date != null) {
            return sales.stream()
                        .filter(sale-> sale.getDate().equals(date))
                        .collect(Collectors.toList());
        }

        return sales;
    }

    
}
