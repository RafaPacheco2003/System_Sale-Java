package system.sales.system_sales.DTO;

import java.util.Date;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import lombok.Data;
import system.sales.system_sales.Entity.Move;
import system.sales.system_sales.Entity.MoveType; // Asegúrate de importar tu enum

@Data
public class MoveDTO {

    private Long id_move;

    @NotNull(message = "La cantidad es obligatoria") // Esto asegura que sea mayor que 0
    @Min(value = 0, message = "La cantidad debe ser mayor o igual a 0") // Cambiado a 0
    @Max(value = 100000, message = "La cantidad no puede ser mayor a 100,000")
    private Integer quantity;

    @NotNull(message = "El usuario es obligatorio")
    private Integer id_usuario;

    private String name_usuario;

    @Temporal(TemporalType.DATE)
    private Date date;

    @NotNull(message = "El producto es obligatorio")
    private Long id_product;

    private String name_product;

    @NotNull(message = "El tipo de movimiento es obligatorio")
    private MoveType moveType; // Agregamos el tipo de movimiento

    public void setFromMove(Move move) {
        if (move.getUsuario() != null) {
            this.id_usuario = move.getUsuario().getId(); // Asegúrate de que este método esté bien definido
            this.name_usuario = move.getUsuario().getNombre(); // Asegúrate de que este método esté bien definido
        }

        if (move.getProduct() != null) {
            this.id_product = move.getProduct().getId_product(); // Asegúrate de que este método esté bien definido
            this.name_product = move.getProduct().getName(); // Asegúrate de que este método esté bien definido
        }

        this.moveType = move.getMoveType(); // Asigna el tipo de movimiento
    }

    public Object map(Object object) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'map'");
    }
}
