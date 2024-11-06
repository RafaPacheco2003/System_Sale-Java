package system.sales.system_sales.Modal.Impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors; // Asegúrate de importar Collectors
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import system.sales.system_sales.DTO.ProductDTO;
import system.sales.system_sales.Entity.Product;
import system.sales.system_sales.Entity.Category; // Importa la entidad correcta
import system.sales.system_sales.Entity.Supplier; // Importa la entidad correcta
import system.sales.system_sales.Exception.DTO.CategoryNotFoundException;
import system.sales.system_sales.Exception.DTO.ProductNotFoundException;
import system.sales.system_sales.Exception.DTO.SupplierNotFoundException;
import system.sales.system_sales.Modal.ProductService;
import system.sales.system_sales.Repository.CategoryRepository;
import system.sales.system_sales.Repository.ProductRepository;
import system.sales.system_sales.Repository.SupplierRepository;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private SupplierRepository supplierRepository;

    public ProductDTO creaProductDTO(ProductDTO productDTO) {
        Product product = modelMapper.map(productDTO, Product.class); // Convierte ProductDTO a Product
        product.setStock(0); // Inicializa el stock en 0

        // Asigna la categoría y el proveedor usando los IDs del ProductDTO
        Category category = categoryRepository.findById(productDTO.getId_category())
                .orElseThrow(() -> new CategoryNotFoundException("Categoria no encontrada"));
        Supplier supplier = supplierRepository.findById(productDTO.getId_supplier())
                .orElseThrow(() -> new SupplierNotFoundException("Supplier no encontrado"));

        product.setCategory(category);
        product.setSupplier(supplier);
        Product saveProduct = productRepository.save(product);

        // Usar el nuevo método para establecer la categoría y el proveedor
        ProductDTO saveProductDTO = modelMapper.map(saveProduct, ProductDTO.class);
        saveProductDTO.setFromProduct(saveProduct); // Asigna directamente los valores desde el objeto Product

        return saveProductDTO;
    }

    @Override
    public Optional<ProductDTO> getProductById(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Producto no encontrado"));

        ProductDTO productDTO = modelMapper.map(product, ProductDTO.class);

        // Usar el nuevo método para establecer la categoría y el proveedor
        productDTO.setFromProduct(product); // Asigna directamente los valores desde el objeto Product

        return Optional.of(productDTO); // Cambiar para devolver un Optional
    }

    @Override
    public ProductDTO updateProduct(Long productId, ProductDTO productDTO) {
        // Buscar el producto existente por su ID
        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Producto no encontrado"));
    
        // Buscar la categoría y el proveedor basados en los IDs proporcionados en productDTO
        Category category = categoryRepository.findById(productDTO.getId_category())
                .orElseThrow(() -> new CategoryNotFoundException("Category no encontrado"));
    
        Supplier supplier = supplierRepository.findById(productDTO.getId_supplier())
                .orElseThrow(() -> new SupplierNotFoundException("Proveedor no encontrado"));
    
        // Actualizar los campos de existingProduct con los valores de productDTO
        existingProduct.setName(productDTO.getName());
        existingProduct.setDescription(productDTO.getDescription());
        existingProduct.setPrice(productDTO.getPrice());
        existingProduct.setStock(productDTO.getStock());
    
        // Actualizar las relaciones con categoría y proveedor
        existingProduct.setCategory(category);
        existingProduct.setSupplier(supplier);
    
        // Actualizar la imagen solo si se proporciona una nueva
        if (productDTO.getImagePath() != null && !productDTO.getImagePath().isEmpty()) {
            existingProduct.setImagePath(productDTO.getImagePath());
        }
    
        // Guardar el producto actualizado en la base de datos
        Product updatedProduct = productRepository.save(existingProduct);
    
        // Retornar el DTO del producto actualizado
        return modelMapper.map(updatedProduct, ProductDTO.class);
    }
    
    @Override
    public List<ProductDTO> getaAllProduct() {
        List<Product> products = productRepository.findAll();
         if (products.isEmpty()) {
            throw new ProductNotFoundException("No hay productos disponibles");
        }

        return products.stream()
                .map(product -> {
                    ProductDTO productDTO = modelMapper.map(product, ProductDTO.class);

                    // Usar el nuevo método para establecer la categoría y el proveedor
                    productDTO.setFromProduct(product); // Asigna directamente los valores desde el objeto Product

                    return productDTO;
                })
                .collect(Collectors.toList());
    }

    @Override
    public void deleteProduct(Long productId) {
        productRepository.deleteById(productId);
    }
}
