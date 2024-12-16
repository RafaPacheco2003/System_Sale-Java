package system.sales.system_sales.Filter.FilterSale;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import system.sales.system_sales.Entity.Sale;
import system.sales.system_sales.Entity.PaymentMethod;  // Asegúrate de importar el enum PaymentMethod

public class PaymentMethodFilter extends SaleFilter {

    @Override
    public List<Sale> filter(List<Sale> sales, Date date, String paymentMethod) {
        if (paymentMethod != null && !paymentMethod.isEmpty()) {
            // Aquí haces referencia al enum PaymentMethod
            PaymentMethod method = PaymentMethod.valueOf(paymentMethod.toUpperCase());
            return sales.stream()
                        .filter(sale -> sale.getPaymentMethod().equals(method))
                        .collect(Collectors.toList());
        }
        return sales;
    }
}
