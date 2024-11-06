package system.sales.system_sales.Modal.Impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import system.sales.system_sales.DTO.DetailsSaleDTO;
import system.sales.system_sales.DTO.SaleDTO;
import system.sales.system_sales.Entity.DetailsSale;
import system.sales.system_sales.Entity.Product;
import system.sales.system_sales.Entity.Sale;
import system.sales.system_sales.Exception.DTO.ProductNotFoundException;
import system.sales.system_sales.Modal.SaleService;
import system.sales.system_sales.Repository.DetailsRepository;
import system.sales.system_sales.Repository.ProductRepository;
import system.sales.system_sales.Repository.SaleRepository;

@Service
public class SaleServiceImpl implements SaleService {

    @Autowired
    private SaleRepository saleRepository;

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private DetailsRepository detailsSaleRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public SaleDTO createSaleDTO(SaleDTO saleDTO) {
        // Mapear SaleDTO a Sale (entidad)
        Sale sale = modelMapper.map(saleDTO, Sale.class);

        // Guardar la venta para obtener el ID (sin detalles aún)
        sale = saleRepository.save(sale);

        double totalAmount = 0;

        // Iterar sobre los detalles de la venta (DetailsSaleDTO)
        for (DetailsSaleDTO detailsDTO : saleDTO.getDetailsSales()) {
            // Buscar el producto por su ID
            Product product = productRepository.findById(detailsDTO.getId_product())
                    .orElseThrow(() -> new ProductNotFoundException(
                            "Producto no encontrado con ID: " + detailsDTO.getId_product()));

            // Verificar si el stock es suficiente
            if (product.getStock() < detailsDTO.getQuantity()) {
                throw new ProductNotFoundException(
                        "Stock insuficiente para el producto con ID: " + product.getId_product());
            }

            // Mapear DetailsSaleDTO a DetailsSale (entidad)
            DetailsSale detailsSale = modelMapper.map(detailsDTO, DetailsSale.class);

            // Asociar el producto y la venta a los detalles
            detailsSale.setProduct(product);
            detailsSale.setSale(sale);

            // Calcular el monto total para los detalles actuales
            double totalPrice = product.getPrice() * detailsDTO.getQuantity();
            detailsSale.setTotalPrice(totalPrice);
            detailsSale.setUnitPrice(product.getPrice());

            // Actualizar el monto total de la venta
            totalAmount += totalPrice;

            // Guardar los detalles de la venta
            detailsSaleRepository.save(detailsSale);

            // (Opcional) Restar la cantidad del stock del producto
            product.setStock(product.getStock() - detailsDTO.getQuantity());
            productRepository.save(product);
        }

        // Asignar el monto total a la venta
        sale.setTotalAmount(totalAmount);

        // Guardar la venta actualizada con el monto total
        sale = saleRepository.save(sale);

        // Retornar la venta mapeada a SaleDTO
        return modelMapper.map(sale, SaleDTO.class);
    }

}
