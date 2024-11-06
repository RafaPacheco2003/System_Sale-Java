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

    private Long id_usuario; // Solo el ID del usuario relacionado
    private Long id_customer; // Solo el ID del cliente relacionado

    private List<DetailsSaleDTO> detailsSales; // Lista de detalles de la venta

    // Configuración del método setFromSale para convertir una entidad Sale a SaleDTO
    public void setFromSale(Sale sale) {
        

            // Asignar el ID del usuario si está disponible
            if (sale.getUsuario() != null) {
                this.id_usuario = sale.getUsuario().getId().longValue(); // Convertir Integer a Long
            }

            // Asignar el ID del cliente si está disponible
            if (sale.getCustomer() != null) {
                this.id_customer = sale.getCustomer().getId_customer(); // Corregido para usar el nombre correcto del campo ID
            }

           

    }
}
