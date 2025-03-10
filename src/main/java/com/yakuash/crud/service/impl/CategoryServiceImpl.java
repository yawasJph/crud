package com.yakuash.crud.service.impl;

import com.yakuash.crud.model.dto.CategoryDto;
import com.yakuash.crud.model.dto.EditCategoryDto;
import com.yakuash.crud.model.entity.Category;
import com.yakuash.crud.model.entity.Product;
import com.yakuash.crud.repository.CategoryRepository;
import com.yakuash.crud.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;


@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final Logger LOGGER = LoggerFactory.getLogger(CategoryServiceImpl.class);

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public void save(CategoryDto categoryDto) {
        Category category = new Category();
        category.setName(categoryDto.getName());
        category.setImage(categoryDto.getImage());
        category.setIsActive(true);
        categoryRepository.save(category);
    }

    @Override
    public Boolean existsCategory(String name) {
        Boolean existsCategory = categoryRepository.existsCategoryByName(name);
        if(existsCategory){
            return true;
        }
        return false;
    }

    @Override
    public String saveImage(MultipartFile file) throws IOException {
        if (!file.isEmpty()) {
        File saveFile = new ClassPathResource("static/img").getFile();
        Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + "category_img" + File.separator
                + file.getOriginalFilename());
        Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        return file.getOriginalFilename();
        }
        return "default.jpg";
    }

    @Override
    public Category findById(Long id) {
        return categoryRepository.findById(id).orElse(null);
    }

    @Override
    public Category edit(EditCategoryDto editCategoryDto,MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            Category oldCategory = categoryRepository.findById(editCategoryDto.getId()).orElse(null);
            if (oldCategory != null) {
                editCategoryDto.setImage(oldCategory.getImage());
            }
            Category category = new Category();
            category.setId(editCategoryDto.getId());
            category.setName(editCategoryDto.getName());
            category.setImage(editCategoryDto.getImage());
            category.setIsActive(editCategoryDto.getIsActive());
            categoryRepository.save(category);
            return category;
        }
        String image = saveImage(file);
        editCategoryDto.setImage(image);
        Category category = new Category();
        category.setId(editCategoryDto.getId());
        category.setName(editCategoryDto.getName());
        category.setImage(editCategoryDto.getImage());
        category.setIsActive(editCategoryDto.getIsActive());
        categoryRepository.save(category);
        return category;
    }

    @Override
    public void deleteById(Long id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public List<Product> findCategoriesContaining(String name) {
        return categoryRepository.findByNameContainingIgnoreCase(name);
    }
}
