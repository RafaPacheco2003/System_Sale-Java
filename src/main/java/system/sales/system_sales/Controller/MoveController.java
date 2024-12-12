package system.sales.system_sales.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import system.sales.system_sales.DTO.MoveDTO;
import system.sales.system_sales.Exception.DTO.MoveNotFoundException;
import system.sales.system_sales.Modal.MoveService;

@RestController
@RequestMapping("/admin/move")
public class MoveController {

    @Autowired
    private MoveService moveService;

    // Crear un nuevo Move
    @PostMapping("/create")
    public ResponseEntity<?> createMove(@Valid @RequestBody MoveDTO moveDTO, HttpServletRequest request) {
    
        String token = request.getHeader("Authorization");
        Integer userId = null;
    
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
    
            userId = moveService.getUserIdFromToken(token);
            System.out.println("Id de usuario ha sido extraído del token: " + userId);
        }
    
        // Asignar el userId al moveDTO
        moveDTO.setId_usuario(userId);
    
        // Crear el movimiento con el DTO actualizado
        MoveDTO createdMove = moveService.createMoveDTO(moveDTO);
    
        return ResponseEntity.status(HttpStatus.CREATED).body("Movimiento creado con ID de producto: " + createdMove.getName_product());
    }
    

    // Obtener un Move por ID
    @GetMapping("/{id}")
    public ResponseEntity<MoveDTO> getMoveById(@PathVariable Long id) {
        return moveService.getMoveById(id)  // Cambia a getMoveById
                .map(dto -> ResponseEntity.ok(dto))
                .orElseThrow(() -> new MoveNotFoundException("Movimiento no encontrado con ID: " + id));
    }
    
    @GetMapping("/all")
    public ResponseEntity<List<MoveDTO>> getAllMove(){
        List<MoveDTO> movesDTO= moveService.getAllMove();

        return ResponseEntity.ok(movesDTO);
    }



    @GetMapping("/type/{type}")
    public ResponseEntity<List<MoveDTO>> getMovesByType(@PathVariable String type){
        List<MoveDTO> movesDTO= moveService.getMoveByType(type);

        return ResponseEntity.ok(movesDTO);
    }

    // Actualizar un Move por ID



  
}
