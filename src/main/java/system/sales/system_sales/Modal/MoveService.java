package system.sales.system_sales.Modal;
import java.util.*;

import system.sales.system_sales.DTO.MoveDTO;

public interface MoveService {
    
    MoveDTO createMoveDTO(MoveDTO moveDTO);

    Optional<MoveDTO> getMoveById(Long moveId);
    
    List<MoveDTO> getAllMove();

    //List
    List<MoveDTO> getMoveByType(String type);

    //Token 
    Integer getUserIdFromToken(String token);


}
