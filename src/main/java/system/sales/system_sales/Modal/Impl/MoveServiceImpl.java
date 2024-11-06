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
import system.sales.system_sales.Modal.MoveService;
import system.sales.system_sales.Repository.MoveRepository;
import system.sales.system_sales.Repository.ProductRepository;
import system.sales.system_sales.Repository.UsuarioRepository;

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

    @Override
    public MoveDTO createMoveDTO(MoveDTO moveDTO) {
        // Convierte MoveDTO a Move
        Move move = modelMapper.map(moveDTO, Move.class);

        // Asigna usuario y producto utilizando sus IDs
        Usuario usuario = usuarioRepository.findById(moveDTO.getId_usuario())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        Product product = productRepository.findById(moveDTO.getId_product())
                .orElseThrow(() -> new ProductNotFoundException("Producto no encontrado"));

        move.setUsuario(usuario);
        move.setProduct(product);

        // Asigna la fecha actual
        move.setDate(new Date()); // Aquí se establece la fecha actual

        // Ajusta el stock del producto según el tipo de movimiento
        adJustProductStock(product, move);

        // Guarda el producto actualizado
        productRepository.save(product);

        // Guarda el movimiento y convierte de nuevo a DTO
        Move savedMove = moveRepository.save(move);

        MoveDTO savedMoveDTO = modelMapper.map(savedMove, MoveDTO.class);
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
        MoveType moveType;

        switch (type.toUpperCase()) {
            case "IN":
                moveType = MoveType.IN;
                break;
            case "OUT":
                moveType = MoveType.OUT;
                break;
            case "ADDJUSTMENT":
                moveType = MoveType.ADDJUSTMENT;
                break;
            default:
                throw new MoveTypeNotFoundException("Tipo de movimiento no encontrado");
        }

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

    /**
     * Utils
     */

    private void adJustProductStock(Product product, Move move) {

        switch (move.getMoveType()) {
            case IN:
                product.setStock(product.getStock() + move.getQuantity());
                break;
            case OUT:
                if (product.getStock() < move.getQuantity()) {
                    throw new InsufficientStockException("Stock insuficientes para el producto");
                }

                product.setStock(product.getStock() - move.getQuantity());
                break;
            case ADDJUSTMENT:
                product.setStock(move.getQuantity());

                break;

            default:
                throw new MoveTypeNotFoundException("Tipo de movimiento no encontrado");
        }
    }

}
