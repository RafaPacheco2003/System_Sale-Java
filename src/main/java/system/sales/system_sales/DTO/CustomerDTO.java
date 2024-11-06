package system.sales.system_sales.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDTO {

    private Long id_customer;

    @NotBlank(message = "El campo 'first name' es obligatorio") // Validación para evitar espacios en blanco
    @Size(min = 2, max = 50, message = "El nombre no puede exceder los 50 caracteres") // Longitud máxima
    private String firstName;

    @Size(min = 2, max = 50, message = "El apellido debe tener entre 2 y 50 caracteres") // Longitud máxima
    private String lastName;


    private Integer age;

   
    private Long phoneNumber;

    @Email(message = "El email debe ser válido") // Validación de formato de email
    private String email;

    private String address;

}
