package system.sales.system_sales.Modal;

import system.sales.system_sales.DTO.SupplierDTO;
import java.util.*;

public interface SupplierService {
    
    /*
     * CRUD
     */
    SupplierDTO createSupplierDTO(SupplierDTO supplierDTO);
    Optional<SupplierDTO> getSupplierByID(Long supplierId);
    SupplierDTO updateSupplier(Long supplierId, SupplierDTO supplierDTO);
    List<SupplierDTO> getAllSupplier();
    void deleteSupplier(Long supplierId);

}
