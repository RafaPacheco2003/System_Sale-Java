package system.sales.system_sales.Factory.MoveType;

import system.sales.system_sales.Entity.MoveType;
import system.sales.system_sales.Exception.DTO.MoveTypeNotFoundException;

public class MoveTypeStrategyFactory {

    public static MoveType getMoveType(String type) {
        
        if (type == null || type.trim().isEmpty()) {
            throw new MoveTypeNotFoundException("El tipo de movimiento no puede ser nulo o vacío.");
        }

        // Deja que valueOf lance IllegalArgumentException si no coincide
        return MoveType.valueOf(type.toUpperCase());
    }
}
