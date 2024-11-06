package system.sales.system_sales.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor

public class DetailsSale {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_detailsService;

    private Integer quantity;

    private double totalPrice;

    private double unitPrice;

    @ManyToOne()
    @JoinColumn(name = "id_product")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "id_sale")
    private Sale sale;
}
