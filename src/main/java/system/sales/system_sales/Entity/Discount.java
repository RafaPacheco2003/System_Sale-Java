package system.sales.system_sales.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Discount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_discount")
    private Long idDiscount;

    @Min(value = 0, message = "El descuento debe ser mayor o igual a 0")
    private double amount; // Descuento en porcentaje o cantidad fija

    @Enumerated(EnumType.STRING)
    private DiscountType type; // Tipo de descuento: PORCENTAJE o CANTIDAD

    @ManyToOne
    @JoinColumn(name = "id_category")
    private Category category; // Si aplica a una categoría específica

    @ManyToOne
    @JoinColumn(name = "id_product")
    private Product product; // Si aplica a un producto específico

    @ManyToOne
    @JoinColumn(name = "id_brand")
    private Supplier supplier; // Si aplica a una marca específica, corregido de "Supplier" a "Brand"

    @Temporal(TemporalType.DATE)
    @Column(name = "expiration_date")
    private Date expirationDate; // Fecha de expiración del descuento

    @PrePersist
    @PreUpdate
    public void validateExpirationDate() {
        if (expirationDate != null && expirationDate.before(new Date())) {
            throw new IllegalArgumentException("La fecha de expiración no puede ser anterior a la fecha actual");
        }
    }
}
