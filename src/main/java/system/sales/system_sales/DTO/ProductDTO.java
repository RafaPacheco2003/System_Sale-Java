package system.sales.system_sales.DTO;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import system.sales.system_sales.Entity.Product;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {

    private Long id_product;

    // Este campo se usa para la subida de imagen durante la creación del producto
    @NotNull(message = "Image cannot be null for creation")
    private MultipartFile image;

    // Este campo será el path de la imagen cuando se responda al cliente
    private String imagePath;

    @NotBlank(message = "Name cannot be blank")
    private String name;

    @NotNull(message = "Description cannot be null")
    @Size(max = 500, message = "Description cannot exceed 500 characters")
    private String description;


    private Integer stock;

    @DecimalMin(value = "0.0", message = "Price must be greater than or equal to 0.0")
    private double price;

    @NotNull(message = "Category ID cannot be null")
    private Long id_category;
    private String name_category;

    @NotNull(message = "Supplier ID cannot be null")
    private Long id_supplier;
    private String name_supplier;

    public void setFromProduct(Product product) {
        if (product.getCategory() != null) {
            this.id_category = product.getCategory().getId_category();
            this.name_category = product.getCategory().getName();
        }

        if (product.getSupplier() != null) {
            this.id_supplier = product.getSupplier().getId_supplier();
            this.name_supplier = product.getSupplier().getName();
        }
    }
}
