package system.sales.system_sales.DTO;

import java.util.Set;

import lombok.Data;


@Data
public class RegisterRequest {
    private String nombre;
    private String email;
    private String password;
    private Set<Integer> roles; // Suponiendo que los roles son identificadores
}


