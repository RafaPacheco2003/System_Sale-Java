package system.sales.system_sales.Filter.FilterSale;

import java.util.Date;
import java.util.List;

import system.sales.system_sales.Entity.Sale;

public abstract class SaleFilter {
    public abstract List<Sale> filter (List<Sale> sales, Date date, String paymentMethod);
}
