package system.sales.system_sales.Entity;

import java.util.List;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Customer")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_customer")
    private Long id_customer;

    @NotBlank(message = "El campo 'first name' es obligatorio") // Validación para evitar espacios en blanco
    @Size(min = 2, max = 50, message = "El nombre no puede exceder los 50 caracteres") // Longitud máxima
    private String firstName;

    @Size(min = 2,max = 50, message = "El apellido no puede exceder los 50 caracteres") // Longitud máxima
    private String lastName;

    @NotNull(message = "La edad es obligatoria") // La edad no puede ser nula
    private Integer age;

   
    private Long phoneNumber;

    @Email(message = "El email debe ser válido") // Validación de formato de email
    private String email;

    private String address;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true) // Corregido para referirse a la propiedad correcta
    private List<Sale> sales; // Cambié a Sale, asumiendo que "sale" es una entidad relacionada

}
