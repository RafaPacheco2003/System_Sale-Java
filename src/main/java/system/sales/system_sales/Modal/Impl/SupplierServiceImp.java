package system.sales.system_sales.Modal.Impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors; // Asegúrate de importar Collectors

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import system.sales.system_sales.DTO.SupplierDTO;
import system.sales.system_sales.Entity.Supplier;
import system.sales.system_sales.Exception.DTO.SupplierNotFoundException;
import system.sales.system_sales.Modal.SupplierService;
import system.sales.system_sales.Repository.SupplierRepository;

@Service
public class SupplierServiceImp implements SupplierService {

    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public SupplierDTO createSupplierDTO(SupplierDTO supplierDTO) {
        Supplier supplier = modelMapper.map(supplierDTO, Supplier.class);
        Supplier savedSupplier = supplierRepository.save(supplier); // Cambiado saveSupplier a savedSupplier

        return modelMapper.map(savedSupplier, SupplierDTO.class);
    }

    @Override
    public Optional<SupplierDTO> getSupplierByID(Long supplierId) { // Cambiado a Long
        return supplierRepository.findById(supplierId)
                .map(supplier -> modelMapper.map(supplier, SupplierDTO.class));
    }

    @Override
public SupplierDTO updateSupplier(Long supplierId, SupplierDTO supplierDTO) {
    // Buscar el proveedor existente por su ID
    Supplier existingSupplier = supplierRepository.findById(supplierId)
            .orElseThrow(() -> new SupplierNotFoundException("Proveedor no encontrado"));

    // Actualizar solo los campos que no incluyen el ID
    existingSupplier.setName(supplierDTO.getName());
    existingSupplier.setTelephone(supplierDTO.getTelephone());
    existingSupplier.setAddress(supplierDTO.getAddress());
    // Si tienes más campos en Supplier, agrégalos aquí

    // Guardar el proveedor actualizado en la base de datos
    Supplier updatedSupplier = supplierRepository.save(existingSupplier);

    // Retornar el DTO del proveedor actualizado
    return modelMapper.map(updatedSupplier, SupplierDTO.class);
}

    @Override
    public List<SupplierDTO> getAllSupplier() {
        List<Supplier> suppliers = supplierRepository.findAll();

        return suppliers.stream()
                .map(supplier -> modelMapper.map(supplier, SupplierDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteSupplier(Long supplierId) { // Cambiado a Long
        if (!supplierRepository.existsById(supplierId)) { // Verificar si el proveedor existe
            throw new SupplierNotFoundException("Supplier not found");
        }
        supplierRepository.deleteById(supplierId);
    }
}
