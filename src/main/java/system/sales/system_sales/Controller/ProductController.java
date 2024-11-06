package system.sales.system_sales.Controller;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import system.sales.system_sales.DTO.ProductDTO;
import system.sales.system_sales.Exception.DTO.CategoryNotFoundException;
import system.sales.system_sales.Exception.DTO.SupplierNotFoundException;
import system.sales.system_sales.ImageStorage.ImageStorage;
import system.sales.system_sales.Modal.ProductService;

@RestController
@RequestMapping("/admin/product")
public class ProductController {

    
    @Value("${storage.location}")
    private String storageLocation;

    @Value("${server.url}")
    private String serverUrl; // URL base del servidor

    @Autowired
    private ImageStorage imageStorage;

    @Autowired
    private ProductService productService;

    // Crear un producto
    @PostMapping("/create")
    public ResponseEntity<?> createProduct(@Valid @ModelAttribute ProductDTO productDTO) {
        // Guarda la imagen si se ha proporcionado
        String imageName = imageStorage.saveImage(productDTO.getImage());
        productDTO.setImagePath(imageName);

        // Llamar al servicio para crear el producto
        ProductDTO createdProduct = productService.creaProductDTO(productDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
    }
    
    // Obtener producto por ID
   /*  @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable Long id) {
        ProductDTO productDTO = productService.getProductById(id);
        productDTO.setImagePath(serverUrl + "/admin/product/images/" + productDTO.getImagePath());
        return ResponseEntity.ok(productDTO);
    }*/

    // Actualizar un producto
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Long id, @Valid @ModelAttribute ProductDTO productDTO) {
        // Guardar la nueva imagen (si se subió)
        String imageName = imageStorage.saveImage(productDTO.getImage());
        productDTO.setImagePath(imageName);

        // Llamar al servicio para actualizar el producto
        ProductDTO updateProductDTO = productService.updateProduct(id, productDTO);

        return ResponseEntity.ok(updateProductDTO);
    }

    // Obtener todos los productos
    @GetMapping("/all")
    public ResponseEntity<?> getAllProducts() {
        List<ProductDTO> productsDTO = productService.getaAllProduct();

       

        // Actualizar la ruta de las imágenes en cada producto
        productsDTO.forEach(product -> {
            if (product.getImagePath() != null) {
                product.setImagePath(serverUrl + "/admin/product/images/" + product.getImagePath());
            }
        });

        return ResponseEntity.ok(productsDTO);
    }

    // Eliminar un producto
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    // Obtener imagen por nombre de archivo
    @GetMapping("/images/{filename:.+}")
    public ResponseEntity<?> getImage(@PathVariable String filename) {
        try {
            Path imagePath = Paths.get(storageLocation).resolve(filename);
            if (!Files.exists(imagePath)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: Imagen no encontrada.");
            }

            byte[] imageBytes = Files.readAllBytes(imagePath);

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_TYPE, Files.probeContentType(imagePath))
                    .body(imageBytes);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al leer la imagen.");
        }
    }
}
