package system.sales.system_sales.DTO;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import system.sales.system_sales.Entity.DetailsSale;
import system.sales.system_sales.Entity.Product;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetailsSaleDTO {

    private Long id_detailsService;

    @NotNull(message = "La cantidad es obligatoria") // Validación para que no sea nula
    @Positive(message = "La cantidad debe ser positiva") // Validación para que sea positiva
    private Integer quantity;

    @NotNull(message = "El precio total es obligatorio") // Validación para que no sea nulo
    @Positive(message = "El precio total debe ser positivo") // Validación para que sea positivo
    private double totalPrice;

    @NotNull(message = "El precio unitario es obligatorio") // Validación para que no sea nulo
    @Positive(message = "El precio unitario debe ser positivo") // Validación para que sea positivo
    private double unitPrice;

    private Long id_product; // Solo el ID del producto relacionado, puedes cambiarlo a ProductDTO si
                             // necesitas más detalles.
    private String name_product;

    private Long id_sale; // Solo el ID de la venta relacionada

    public void setFormDetailsSale(DetailsSale detailsSale) {

        // Asumiendo que Product tiene un método getId() y getName()
        if (detailsSale.getProduct() != null) {
            this.id_product = detailsSale.getProduct().getId_product(); // ID del producto
            this.name_product = detailsSale.getProduct().getName(); // Nombre del producto
        }

        // Asumiendo que Sale tiene un método getId_sale()
        if (detailsSale.getSale() != null) {
            this.id_sale = detailsSale.getSale().getId_sale(); // ID de la venta
        }

    }

}
