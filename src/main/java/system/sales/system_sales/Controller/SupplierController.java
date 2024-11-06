package system.sales.system_sales.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import system.sales.system_sales.DTO.SupplierDTO;
import system.sales.system_sales.Exception.DTO.SupplierNotFoundException;
import system.sales.system_sales.Modal.SupplierService;

@RestController
@RequestMapping("/admin/supplier")
public class SupplierController {

    @Autowired
    private SupplierService supplierService;

    @PostMapping("/create")
    public ResponseEntity<SupplierDTO> createSupplier(@Valid @RequestBody SupplierDTO supplierDTO) {
        SupplierDTO createdSupplier = supplierService.createSupplierDTO(supplierDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSupplier);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SupplierDTO> getSupplierById(@PathVariable Long id) {
        return supplierService.getSupplierByID(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new SupplierNotFoundException("Proveedor no encontrado")); // Lanzar excepción personalizada
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<SupplierDTO> updateSupplier(@PathVariable Long id,
            @Valid @RequestBody SupplierDTO supplierDTO) {
        SupplierDTO updatedSupplierDTO = supplierService.updateSupplier(id, supplierDTO);
        return ResponseEntity.ok(updatedSupplierDTO);
    }

    @GetMapping("/all")
    public ResponseEntity<List<SupplierDTO>> getAllSupplier() {
        List<SupplierDTO> suppliersDTO = supplierService.getAllSupplier();
        if (suppliersDTO.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(suppliersDTO);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteSupplier(@PathVariable Long id) {
        supplierService.deleteSupplier(id);
        return ResponseEntity.noContent().build();
    }
}
