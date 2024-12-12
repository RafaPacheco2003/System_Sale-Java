package system.sales.system_sales.Modal.Impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import system.sales.system_sales.DTO.DetailsSaleDTO;
import system.sales.system_sales.Entity.DetailsSale;
import system.sales.system_sales.Entity.Product;
import system.sales.system_sales.Entity.Sale;
import system.sales.system_sales.Exception.DTO.ProductNotFoundException;
import system.sales.system_sales.Modal.DetailsSaleService;
import system.sales.system_sales.Repository.DetailsRepository;
import system.sales.system_sales.Repository.ProductRepository;

@Service
public class DetailsSaleServiceImpl implements DetailsSaleService{

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ModelMapper modelMapper;

     @Autowired
    private DetailsRepository detailsSaleRepository;
    
    @Override
    public void addDetailsToSale(Sale sale, List<DetailsSaleDTO> detailsSales) {
        for(DetailsSaleDTO detailsDTO: detailsSales){
            Product product = productRepository.findById(detailsDTO.getId_product())
                            .orElseThrow(()-> new ProductNotFoundException("Producto no encontrado con Id: " + detailsDTO.getId_product()));

            DetailsSale detailsSale= modelMapper.map(detailsDTO, DetailsSale.class);
            
            // Asociar el producto y la venta a los detalles
            detailsSale.setProduct(product);
            detailsSale.setSale(sale);

            // Calcular el monto total para el detalle
            double totalPrice = product.getPrice() * detailsDTO.getQuantity();
            detailsSale.setTotalPrice(totalPrice);
            detailsSale.setUnitPrice(product.getPrice());

            // Guardar los detalles de la venta
            detailsSaleRepository.save(detailsSale);
            
            // Actualizar el stock del producto
            product.setStock(product.getStock() - detailsDTO.getQuantity());
            productRepository.save(product);
        }
    }
    
}
