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
    private Double totalPrice; // Cambia de 'double' a 'Double' para permitir valores nulos

    @NotNull(message = "El precio unitario es obligatorio") // Validación para que no sea nulo
    @Positive(message = "El precio unitario debe ser positivo") // Validación para que sea positivo
    private Double unitPrice;  // Cambia de 'double' a 'Double' para permitir valores nulos

    private Long id_product; // Solo el ID del producto relacionado
    private String name_product;

    private Long id_sale; // Solo el ID de la venta relacionada

    // Método para mapear los datos de DetailsSale a DetailsSaleDTO
    public void setFormDetailsSale(DetailsSale detailsSale) {
        // Mapeo de producto
        if (detailsSale.getId_detailsService() != null) {
            this.id_detailsService = detailsSale.getId_detailsService();
        }
        if (detailsSale.getProduct() != null) {
            this.id_product = detailsSale.getProduct().getId_product();
            this.name_product = detailsSale.getProduct().getName();
        }
    
        // Mapeo de venta
        if (detailsSale.getSale() != null) {
            this.id_sale = detailsSale.getSale().getId_sale();
        }
    
        // Mapeo de detalles específicos
        if (detailsSale.getQuantity() != null) {
            this.quantity = detailsSale.getQuantity();
        }
    
        // Verificar si totalPrice no es nulo y asignarlo
        if (detailsSale.getTotalPrice() != 0.0) {
            this.totalPrice = detailsSale.getTotalPrice(); // Ahora totalPrice es un Double, que puede ser null
        }
    
        // Verificar si unitPrice no es nulo y asignarlo
        if (detailsSale.getUnitPrice() != 0.0) {
            this.unitPrice = detailsSale.getUnitPrice(); // Ahora unitPrice es un Double, que puede ser null
        }
    }
    

}
