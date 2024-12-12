package system.sales.system_sales.DTO;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import system.sales.system_sales.Entity.PaymentMethod;
import system.sales.system_sales.Entity.Sale;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaleDTO {

    private Long id_sale;

    @NotNull(message = "La fecha es obligatoria") 
    private Date date;

    @NotNull(message = "El método de pago es obligatorio") 
    private PaymentMethod paymentMethod;

    @NotNull(message = "El monto total es obligatorio") 
    @Positive(message = "El monto total debe ser positivo") 
    private double totalAmount;

    @NotNull(message = "El monto recibido es obligatorio") 
    @Positive(message = "El monto recibido debe ser positivo") 
    private double receivedAmount;

    @NotNull(message = "El cambio es obligatorio") 
    @Positive(message = "El cambio debe ser positivo") 
    private double change;

    private Integer id_usuario; // Solo el ID del usuario relacionado
    private Long id_customer; // Solo el ID del cliente relacionado

    private List<DetailsSaleDTO> detailsSales; // Lista de detalles de la venta

    // Configuración del método setFromSale para convertir una entidad Sale a SaleDTO
    public void setFromSale(Sale sale) {
        // Mapeo de usuario
        if (sale.getUsuario() != null) {
            this.id_usuario = sale.getUsuario().getId(); 
        }
    
        // Mapeo de cliente
        if (sale.getCustomer() != null) {
            this.id_customer = sale.getCustomer().getId_customer(); 
        }
    
        // Mapeo de detalles de venta
        this.detailsSales = sale.getDetailsSales().stream()
            .map(detailsSale -> {
                DetailsSaleDTO detailsSaleDTO = new DetailsSaleDTO();
                detailsSaleDTO.setFormDetailsSale(detailsSale);
                return detailsSaleDTO;
            })
            .collect(Collectors.toList());
    }
    
}
