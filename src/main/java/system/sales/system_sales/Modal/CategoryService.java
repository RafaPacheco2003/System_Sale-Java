package system.sales.system_sales.Modal;

import java.util.List;
import java.util.Optional;

import system.sales.system_sales.DTO.CategoryDTO;


public interface CategoryService {
    
    /*
     * CRUD
     */
    CategoryDTO createCategory(CategoryDTO categoryDTO);

    Optional<CategoryDTO> getCategoryById(Long categoryId);

    CategoryDTO updateCategory(Long categoryId, CategoryDTO categoryDTO);

    List<CategoryDTO> getAllCategory();
    
    void deleteCategory(Long categoryId);

}