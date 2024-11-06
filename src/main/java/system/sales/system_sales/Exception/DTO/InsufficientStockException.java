package system.sales.system_sales.Exception.DTO;

public class InsufficientStockException extends IllegalArgumentException{
     public InsufficientStockException(String message) {
        super(message);
    }
}
