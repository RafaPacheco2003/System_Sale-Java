package system.sales.system_sales.Exception.DTO;

public class MoveNotFoundException extends RuntimeException{
    public MoveNotFoundException(String message){
        super(message);
    }
}
