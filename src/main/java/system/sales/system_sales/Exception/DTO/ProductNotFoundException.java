package system.sales.system_sales.Exception.DTO;

public class ProductNotFoundException extends RuntimeException{
    public ProductNotFoundException(String message){
        super(message);
    }
    
}
