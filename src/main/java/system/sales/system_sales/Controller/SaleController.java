package system.sales.system_sales.Controller;

import java.util.Date;
import java.util.List;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import system.sales.system_sales.DTO.SaleDTO;
import system.sales.system_sales.Modal.SaleService;
import system.sales.system_sales.Response.SalesResponse;

@RestController
@RequestMapping("/admin/sale")
public class SaleController {

    @Autowired
    private SaleService saleService;

    @PostMapping("/create")
    public ResponseEntity<?> createSale(@RequestBody SaleDTO saleDTO, HttpServletRequest request){
        
        String token = request.getHeader("Authorization");
        Integer userId = null;

        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);

            userId = saleService.getIdUserFromToken(token);
            System.out.println("Id de usuario ha sido extraído del token: " + userId);
        }
        
        saleDTO.setId_usuario(userId);
       
        SaleDTO createSale =  saleService.createSaleDTO(saleDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("Venta creada con Id: " + createSale.getId_sale());
    }

    @GetMapping("/{id}")
    public SaleDTO getSaleById(@PathVariable("id") Long id) {
        return saleService.getByIdSale(id).orElseThrow();
    }

    @GetMapping("/all")
    public ResponseEntity<List<SaleDTO>> getAllSales(){
        List<SaleDTO> salesDTO= saleService.getAllSale();

        return ResponseEntity.ok(salesDTO);
    }

    @GetMapping("/paymentMethod")
    public ResponseEntity<List<SaleDTO>> getAllPaymentMethodSales(String paymentMethod){
        List<SaleDTO> salesDTO = saleService.getSaleByPaymentMethod(paymentMethod);

        return ResponseEntity.ok(salesDTO);
    }

    @GetMapping("/total-by-filter")
public ResponseEntity<SalesResponse> getSalesTotalAmountByFilter(
        @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date date,
        @RequestParam(required = false) String paymentMethod) {
    try {
        SalesResponse response;

        // Si no se proporcionan parámetros, devuelve todas las ventas
        if (date == null && paymentMethod == null) {
            response = saleService.getSalesTotalAmountByFilter(null, null);
        } else {
            response = saleService.getSalesTotalAmountByFilter(date, paymentMethod);
        }

        return ResponseEntity.ok(response);
    } catch (RuntimeException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
}




    
}
