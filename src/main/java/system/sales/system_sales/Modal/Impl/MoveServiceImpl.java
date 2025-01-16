package system.sales.system_sales.Modal.Impl;

import java.util.*;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import system.sales.system_sales.DTO.MoveDTO;
import system.sales.system_sales.Entity.Move;
import system.sales.system_sales.Entity.MoveType;
import system.sales.system_sales.Entity.Product;
import system.sales.system_sales.Entity.Usuario;
import system.sales.system_sales.Exception.DTO.InsufficientStockException;
import system.sales.system_sales.Exception.DTO.MoveNotFoundException;
import system.sales.system_sales.Exception.DTO.MoveTypeNotFoundException;
import system.sales.system_sales.Exception.DTO.ProductNotFoundException;
import system.sales.system_sales.Factory.MoveType.MoveTypeStrategyFactory;
import system.sales.system_sales.Modal.MoveService;
import system.sales.system_sales.Repository.MoveRepository;
import system.sales.system_sales.Repository.ProductRepository;
import system.sales.system_sales.Repository.UsuarioRepository;
import system.sales.system_sales.Security.TokenUtils;

import system.sales.system_sales.Strategy.MoveStock.StockAdjustmentStrategy;

@Service
public class MoveServiceImpl implements MoveService {

    @Autowired
    private MoveRepository moveRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private Map<MoveType, StockAdjustmentStrategy> moveMap;

    @Override
    public MoveDTO createMoveDTO(MoveDTO moveDTO) {
        // Convierte MoveDTO a Move
        Move move = new Move();
        move.setQuantity(moveDTO.getQuantity());

        // Asigna usuario y producto utilizando sus IDs
        Usuario usuario = usuarioRepository.findById(moveDTO.getId_usuario())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        Product product = productRepository.findById(moveDTO.getId_product())
                .orElseThrow(() -> new ProductNotFoundException("Producto no encontrado"));

        move.setUsuario(usuario);
        move.setProduct(product);
        move.setDate(new Date()); // Fecha actual

        // Asegúrate de que moveType esté correctamente asignado desde el DTO
        MoveType moveType = moveDTO.getMoveType();
        if (moveType == null) {
            throw new MoveTypeNotFoundException("Tipo de movimiento no especificado");
        }

        move.setMoveType(moveType);

        // Obtener la estrategia de ajuste de stock
        StockAdjustmentStrategy strategy = moveMap.get(moveType);
        if (strategy == null) {
            throw new MoveTypeNotFoundException("Estrategia no encontrada para el tipo de movimiento: " + moveType);
        }

        // Ajustar el stock del producto usando la estrategia correspondiente
        strategy.adjust(product, move);

        // Guardar producto actualizado
        productRepository.save(product);

        // Guardar el movimiento
        Move savedMove = moveRepository.save(move);

        // Convertir el movimiento guardado a DTO y devolverlo
        MoveDTO savedMoveDTO = new MoveDTO();
        savedMoveDTO.setFromMove(savedMove);
        return savedMoveDTO;
    }

    @Override
    public Optional<MoveDTO> getMoveById(Long moveId) {
        return moveRepository.findById(moveId)
                .map(move -> {
                    MoveDTO moveDTO = modelMapper.map(move, MoveDTO.class);

                    moveDTO.setFromMove(move);
                    return moveDTO;

                }); // Devuelves un Optional<MoveDTO>
    }

    @Override
    public List<MoveDTO> getAllMove() {
        List<Move> moves = moveRepository.findAll();
        if (moves.isEmpty()) {
            throw new MoveNotFoundException("No se encontraron movimientos.");
        }
        return moves.stream()
                .map(move -> {
                    MoveDTO moveDTO = modelMapper.map(move, MoveDTO.class);

                    moveDTO.setFromMove(move);
                    return moveDTO;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<MoveDTO> getMoveByType(String type) {

        // Utiliza la fábrica para obtener el tipo de movimiento
        MoveType moveType = MoveTypeStrategyFactory.getMoveType(type);

        // Buscar los movimientos por tipo
        List<Move> moves = moveRepository.findByMoveType(moveType);

        // Verificar si la lista está vacía y lanzar excepción si es necesario
        if (moves.isEmpty()) {
            throw new MoveNotFoundException("No se encontraron movimientos de tipo: " + type);
        }

        // Mapear y devolver la lista de MoveDTO
        return moves.stream()
                .map(move -> {
                    MoveDTO moveDTO = modelMapper.map(move, MoveDTO.class);
                    moveDTO.setFromMove(move);
                    return moveDTO;
                })
                .collect(Collectors.toList());
    }

    @Override
    public Integer getUserIdFromToken(String token) {
        return TokenUtils.getUserIdFromToken(token);
    }

}
