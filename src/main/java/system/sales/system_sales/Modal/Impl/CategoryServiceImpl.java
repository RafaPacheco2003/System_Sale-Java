package system.sales.system_sales.Modal.Impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import system.sales.system_sales.DTO.CategoryDTO;
import system.sales.system_sales.Modal.CategoryService;
import system.sales.system_sales.Repository.CategoryRepository;
import system.sales.system_sales.Entity.Category; // Importación corregida
import system.sales.system_sales.Exception.DTO.CategoryNotFoundException;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {

        Category category = modelMapper.map(categoryDTO, Category.class);

        Category savedCategory = categoryRepository.save(category);
        
        return modelMapper.map(savedCategory, CategoryDTO.class);
    }

    @Override
    public Optional<CategoryDTO> getCategoryById(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .map(category -> modelMapper.map(category, CategoryDTO.class));
    }

    public CategoryDTO updateCategory(Long categoryId, CategoryDTO categoryDTO) {
        // Buscar la categoría existente por su ID
        Category existingCategory = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException("Categoría no encontrada"));
    
        // Crear una configuración personalizada de ModelMapper para ignorar el campo id_category
        modelMapper.typeMap(CategoryDTO.class, Category.class)
                .addMappings(mapper -> mapper.skip(Category::setId_category));  // Ignorar el mapeo del ID
    
        // Mapear el DTO a la entidad existente sin modificar el ID
        modelMapper.map(categoryDTO, existingCategory);
    
        // Guardar la categoría actualizada en la base de datos
        Category updatedCategory = categoryRepository.save(existingCategory);
    
        // Retornar el DTO de la categoría actualizada
        return modelMapper.map(updatedCategory, CategoryDTO.class);
    }
    

    @Override
    public List<CategoryDTO> getAllCategory() {
        List<Category> categories = categoryRepository.findAll();

        if (categories.isEmpty()) {
            throw new CategoryNotFoundException("No hay categorias disponibles");
        }
        return categories.stream()
                .map(category -> modelMapper.map(category, CategoryDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteCategory(Long categoryId) {
        // Verificar si la categoría existe
        if (!categoryRepository.existsById(categoryId)) {
            throw new CategoryNotFoundException("Categoría no encontrada"); // Lanzar excepción personalizada
        }

        // Si existe, eliminar la categoría
        categoryRepository.deleteById(categoryId);
    }

}
