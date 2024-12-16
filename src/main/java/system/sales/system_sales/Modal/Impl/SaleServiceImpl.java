package system.sales.system_sales.Modal.Impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import system.sales.system_sales.DTO.SaleDTO;
import system.sales.system_sales.Entity.Customer;
import system.sales.system_sales.Entity.PaymentMethod;

import system.sales.system_sales.Entity.Sale;
import system.sales.system_sales.Entity.Usuario;
import system.sales.system_sales.Exception.DTO.SaleNotFoundException;
import system.sales.system_sales.Filter.FilterSale.SaleFilter;
import system.sales.system_sales.Modal.DetailsSaleService;
import system.sales.system_sales.Modal.FinancialCalculationService;
import system.sales.system_sales.Modal.SaleService;
import system.sales.system_sales.Repository.CustomerRepository;
import system.sales.system_sales.Repository.DetailsRepository;
import system.sales.system_sales.Repository.ProductRepository;
import system.sales.system_sales.Repository.SaleRepository;
import system.sales.system_sales.Repository.UsuarioRepository;
import system.sales.system_sales.Response.SalesResponse;
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
    private UsuarioRepository usuarioRepository;
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private List<SaleFilter> filters; // Inyección de los filtros

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private FinancialCalculationService financialCalculationService;


    public SaleDTO createSaleDTO(SaleDTO saleDTO) {
        // Mapear SaleDTO a Sale (entidad)
        Sale sale = modelMapper.map(saleDTO, Sale.class);
        
        Usuario usuario = usuarioRepository.findById(saleDTO.getId_usuario())
                .orElseThrow(() -> new SaleNotFoundException("Usuario no encontrado" ));
        Customer customer = customerRepository.findById(saleDTO.getId_customer())
                .orElseThrow(() -> new SaleNotFoundException("Cliente no encotrado"));
        
        sale.setCustomer(customer);
        sale.setDate(new Date());
        sale.setUsuario(usuario);
        // Guardar la venta inicial para obtener el ID (sin detalles aún)
        sale = saleRepository.save(sale);
        
        double totalAmount = financialCalculationService.calculateTotalSaleAmount(saleDTO.getDetailsSales());
               
        // Asociar los detalles de la venta a la venta
        detailsSaleService.addDetailsToSale(sale, saleDTO.getDetailsSales());
        
        // Asignar el monto total a la venta
        sale.setTotalAmount(totalAmount);

        // Calcular el cambio (changeAmount)
        double receivedAmount = saleDTO.getReceivedAmount();  // Asegúrate de tener este campo en SaleDTO
        double changeAmount = receivedAmount - totalAmount;

        sale.setChangeAmount(changeAmount);
        
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
    
    @Override
    public List<SaleDTO> getSaleByDate(Date date) {
        // Buscar las ventas por fecha
        List<Sale> sales = saleRepository.findByDate(date);
    
        if (sales.isEmpty()) {
            throw new RuntimeException("No se encontraron ventas en la fecha proporcionada");
        }

        // Mapear la lista de ventas a SaleDTO
        return sales.stream()
            .map(sale -> {
                SaleDTO saleDTO = modelMapper.map(sale, SaleDTO.class);
                saleDTO.setFromSale(sale);
                return saleDTO;
        })
        .collect(Collectors.toList());
    }

    @Override
    public SalesResponse getSalesAndTotalByDate(Date date) {
        // Reutilizamos el método getSaleByDate para obtener la lista de ventas
        List<SaleDTO> salesDTOList = getSaleByDate(date);
    
        // Calculamos el total de las ventas en esa fecha
        double totalAmount = salesDTOList.stream()
           .mapToDouble(SaleDTO::getTotalAmount)
            .sum();

        // Retornamos la respuesta con la lista de ventas y el total
        return new SalesResponse(salesDTOList, totalAmount);
    }

    @Override
    public SalesResponse getSalesTotalAmountByFilter(Date date, String paymentMethod) {
        List<Sale> sales;
    
        // Si no se reciben filtros, obtener todas las ventas
        if (date == null && paymentMethod == null) {
            sales = saleRepository.findAll();
        } else {
            // Aplicar los filtros solo si se proporcionan
            sales = saleRepository.findAll();
    
            for (SaleFilter filter : filters) {
                sales = filter.filter(sales, date, paymentMethod);
            }
        }
    
        if (sales.isEmpty()) {
            throw new RuntimeException("No se encontraron ventas con los criterios proporcionados");
        }
    
        // Mapear las ventas a DTO
        List<SaleDTO> saleDTOList = sales.stream()
            .map(s -> {
                SaleDTO saleDTO = modelMapper.map(s, SaleDTO.class);
                saleDTO.setFromSale(s);
                return saleDTO;
            })
            .collect(Collectors.toList());
    
        double totalAmount = saleDTOList.stream()
            .mapToDouble(SaleDTO::getTotalAmount)
            .sum();
    
        return new SalesResponse(saleDTOList, totalAmount);
    }
    





    @Override
    public Integer getIdUserFromToken(String token) {
        return TokenUtils.getUserIdFromToken(token);
    }



}
