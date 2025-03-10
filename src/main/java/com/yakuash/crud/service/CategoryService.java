package com.yakuash.crud.service;

import com.yakuash.crud.model.dto.CategoryDto;
import com.yakuash.crud.model.dto.EditCategoryDto;
import com.yakuash.crud.model.entity.Category;
import com.yakuash.crud.model.entity.Product;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;

public interface CategoryService {

    List<Category> getAllCategories();
    void save(CategoryDto categoryDto);
    Boolean existsCategory(String name);
    String saveImage(MultipartFile file) throws IOException;
    Category findById(Long id);
    Category edit(EditCategoryDto editCategoryDto,MultipartFile file) throws IOException;
    void deleteById(Long id);
    List<Product> findCategoriesContaining(String name);
}
