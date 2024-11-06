package system.sales.system_sales.Modal;

import java.util.List;
import java.util.Optional;

import system.sales.system_sales.DTO.ProductDTO;

public interface ProductService {

    /*
     * CRUD
     */
    ProductDTO creaProductDTO (ProductDTO productDTO);
    Optional<ProductDTO> getProductById(Long productId);
    ProductDTO updateProduct(Long productId, ProductDTO productDTO);
    List<ProductDTO> getaAllProduct();
    void deleteProduct(Long productId);

    

}
