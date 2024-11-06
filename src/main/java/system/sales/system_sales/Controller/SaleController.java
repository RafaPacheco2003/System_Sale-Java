package system.sales.system_sales.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import system.sales.system_sales.DTO.SaleDTO;
import system.sales.system_sales.Modal.SaleService;

@RestController
@RequestMapping("/admin/sale")
public class SaleController {

    @Autowired
    private SaleService saleService;

    @PostMapping("/create")
    public ResponseEntity<?> createSale(@RequestBody SaleDTO saleDTO){
        SaleDTO createSale= saleService.createSaleDTO(saleDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("Venta creada con Id: " + createSale.getId_sale());
    }
    
}
