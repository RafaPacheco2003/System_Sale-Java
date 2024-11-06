package system.sales.system_sales.DTO;



import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SupplierDTO {
    
     private Long id_supplier;

    @NotNull(message = "El nombre es obligatorio")
    @Size(min = 2, max = 50, message = "El nombre debe tener entre 2 y 50 caracteres")
    private String name;

    @NotNull(message = "El teléfono es obligatorio")
    private Long telephone;
    @Size(max = 100, message = "La dirrecion no puede tener mas de 100 caracteres")
    @NotNull(message = "El teléfono es obligatorio")
    private String address;

   
}
