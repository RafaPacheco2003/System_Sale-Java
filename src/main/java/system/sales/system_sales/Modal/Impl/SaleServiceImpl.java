package system.sales.system_sales.Modal.Impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import system.sales.system_sales.DTO.SaleDTO;

import system.sales.system_sales.Entity.PaymentMethod;

import system.sales.system_sales.Entity.Sale;

import system.sales.system_sales.Exception.DTO.SaleNotFoundException;
import system.sales.system_sales.Modal.DetailsSaleService;
import system.sales.system_sales.Modal.FinancialCalculationService;
import system.sales.system_sales.Modal.SaleService;
import system.sales.system_sales.Repository.DetailsRepository;
import system.sales.system_sales.Repository.ProductRepository;
import system.sales.system_sales.Repository.SaleRepository;
import system.sales.system_sales.Security.TokenUtils;

@Service
public class SaleServiceImpl implements SaleService {

    @Autowired
    private SaleRepository saleRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private DetailsRepository detailsSaleRepository;
    @Autowired
    private DetailsSaleService detailsSaleService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private FinancialCalculationService financialCalculationService;

    public SaleDTO createSaleDTO(SaleDTO saleDTO) {
        // Mapear SaleDTO a Sale (entidad)
        Sale sale = modelMapper.map(saleDTO, Sale.class);
        
        // Guardar la venta inicial para obtener el ID (sin detalles aún)
        sale = saleRepository.save(sale);
        
        double totalAmount = financialCalculationService.calculateTotalSaleAmount(saleDTO.getDetailsSales());
               
        // Asociar los detalles de la venta a la venta
        detailsSaleService.addDetailsToSale(sale, saleDTO.getDetailsSales());
        
        // Asignar el monto total a la venta
        sale.setTotalAmount(totalAmount);
        
        // Guardar la venta actualizada con el monto total
        sale = saleRepository.save(sale);

        // Retornar la venta mapeada a SaleDTO
        return modelMapper.map(sale, SaleDTO.class);
    }

    

    
    

    @Override
    public Optional<SaleDTO> getByIdSale(Long id_sale) {
        Optional<Sale> sale = saleRepository.findById(id_sale);
    
        // Lanzamos una excepción si no se encuentra la venta
        if (!sale.isPresent()) {
            throw new RuntimeException("No se encontro ha encontrado la venta");
        }
    
        // Usamos map para convertir el Sale en un SaleDTO
        return sale.map(s -> {
            SaleDTO saleDTO = modelMapper.map(s, SaleDTO.class);
            saleDTO.setFromSale(s);
            return saleDTO;
        });
    }
    

    @Override
    public List<SaleDTO> getAllSale() {
       List<Sale> sales= saleRepository.findAll();

       if (sales.isEmpty()) {
            throw new RuntimeException("No hay ventas disponibles");

       }

       return sales.stream()
                .map(sale ->{
                    SaleDTO saleDTO = modelMapper.map(sale, SaleDTO.class);

                    saleDTO.setFromSale(sale);
                    return saleDTO;
                })
                .collect(Collectors.toList());
    }






    @Override
    public Integer getIdUserFromToken(String token) {
        return TokenUtils.getUserIdFromToken(token);
    }

    @Override
    public List<SaleDTO> getSaleByPaymentMethod(String paymentMethod) {
        PaymentMethod method = PaymentMethod.valueOf(paymentMethod.toUpperCase());
    
        List<Sale> sales = saleRepository.findByPaymentMethod(method);
    
        if (sales.isEmpty()) {
            throw new SaleNotFoundException("No se encontraron ventas con el metodo de pago: " + paymentMethod);
        }
    
        return sales.stream()
            .map(sale -> {
                SaleDTO saleDTO = modelMapper.map(sale, SaleDTO.class);
                saleDTO.setFromSale(sale);  // Esto asegura que los detalles se mapeen correctamente
                return saleDTO;
            })
            .collect(Collectors.toList());
    }
    




    
    

   

}
