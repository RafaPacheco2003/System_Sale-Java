package system.sales.system_sales.Modal.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import system.sales.system_sales.DTO.DetailsSaleDTO;
import system.sales.system_sales.Entity.Product;
import system.sales.system_sales.Exception.DTO.ProductNotFoundException;
import system.sales.system_sales.Modal.FinancialCalculationService;
import system.sales.system_sales.Repository.ProductRepository;

@Service
public class FinancialCalculationServiceImpl implements FinancialCalculationService {

    @Autowired
    private ProductRepository productRepository;


    @Override
    public double calculateTotalSaleAmount(List<DetailsSaleDTO> detailsSales) {
       double totalAmount= 0;

        for (DetailsSaleDTO detailsDTO : detailsSales){

            Product product = productRepository.findById(detailsDTO.getId_product())
                                .orElseThrow(()-> new ProductNotFoundException(
                                    "Producto no encontrado con Id: " +detailsDTO.getId_product()
                                ));

            if (product.getStock() < detailsDTO.getQuantity()) {
                throw new ProductNotFoundException(
                    "Stock insifucientes para el producto con el Id:" + detailsDTO.getId_product()
                );
            }

            //Calucla el monto total para el detalle actual
            double totalPrice= product.getPrice() * detailsDTO.getQuantity();

            totalAmount += totalPrice;
        }

        return totalAmount;
    }
    
}
