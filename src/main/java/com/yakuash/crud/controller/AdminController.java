package com.yakuash.crud.controller;

import com.yakuash.crud.model.dto.CategoryDto;
import com.yakuash.crud.model.dto.EditCategoryDto;
import com.yakuash.crud.model.dto.EditProductDto;
import com.yakuash.crud.model.dto.ProductDto;
import com.yakuash.crud.model.entity.Category;
import com.yakuash.crud.service.CategoryService;
import com.yakuash.crud.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.io.IOException;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final Logger LOGGER = LoggerFactory.getLogger(AdminController.class);
    private final CategoryService categoryService;
    private final ProductService productService;

    @GetMapping("")
    public String home(){
        return "admin/home";
    }

    @GetMapping("/create-category")
    public String formProduct(){
        return "admin/createCategory";
    }

    @PostMapping("/create-category")
    public String createCategory(CategoryDto categoryDto,@RequestParam MultipartFile file,RedirectAttributes redirectAttributes,Model model) throws IOException {
        Boolean existsCategory = categoryService.existsCategory(categoryDto.getName());
        if(existsCategory){
            redirectAttributes.addFlashAttribute("exists","Category already exists");
            return "redirect:/admin/create-category";
        }

        String image = categoryService.saveImage(file);
        categoryDto.setImage(image);
        categoryService.save(categoryDto);
        model.addAttribute("category",categoryDto);
        redirectAttributes.addFlashAttribute("success","Category saved successfully");
        return "redirect:/admin/create-category";
    }

    @GetMapping("/categories")
    public String showCategories(Model model){
        model.addAttribute("categories",categoryService.getAllCategories());
        return "admin/showCategories";
    }

    @GetMapping("/edit-category/{id}")
    public String formEditCategory(@PathVariable Long id,Model model,RedirectAttributes redirectAttributes){
        Category category = categoryService.findById(id);
        if(category == null){
            redirectAttributes.addFlashAttribute("notFound","category not found");
            return "redirect:/admin/categories";
        }
        model.addAttribute("category",category);
        return "admin/editCategory";
    }

    @PostMapping("/edit-category")
    public String editCategory(EditCategoryDto editCategoryDto,@RequestParam MultipartFile file,RedirectAttributes redirectAttributes) throws IOException {
        Category category = categoryService.edit(editCategoryDto,file);
        redirectAttributes.addFlashAttribute("success","Category update successfully");
        return "redirect:/admin/edit-category/"+category.getId();
    }

    @GetMapping("/delete-category/{id}")
    public String deleteCategory(@PathVariable Long id,RedirectAttributes redirectAttributes){
        Category category = categoryService.findById(id);
        if (category == null){
            redirectAttributes.addFlashAttribute("notFound","category not found");
            return "redirect:/admin/categories";
        }
        categoryService.deleteById(id);
        redirectAttributes.addFlashAttribute("success","Category delete successfully");
        return "redirect:/admin/categories";
    }

    @GetMapping("/create-product")
    public String formCreateProduct(Model model){
        model.addAttribute("categories" , categoryService.getAllCategories());
        return "admin/createProduct";
    }

    @PostMapping("/create-product")
    public String createProduct(ProductDto productDto, RedirectAttributes redirectAttributes,Model model,@RequestParam MultipartFile file) throws IOException {
        Boolean existsProduct = productService.existsProduct(productDto.getName());
        if(existsProduct){
            redirectAttributes.addFlashAttribute("exists","Product already exists");
            return "redirect:/admin/create-product";
        }
        String image = productService.saveImage(file);
        productDto.setImage(image);
        productService.save(productDto);
        redirectAttributes.addFlashAttribute("success","Product create successfully");
        return "redirect:/admin/create-product";
    }

    @GetMapping("/products")
    public String products(Model model){
        model.addAttribute("products",productService.getAll());
        return "admin/showProducts";
    }

    @GetMapping("/edit-product/{id}")
    public String editProduct(@PathVariable Long id,Model model){
        model.addAttribute("product",productService.findById(id));
        model.addAttribute("categories",categoryService.getAllCategories());
        return "admin/editProduct";
    }

    @PostMapping("/edit-product")
    public String updateProduct(EditProductDto editProductDto,@RequestParam MultipartFile file,RedirectAttributes redirectAttributes) throws IOException {
        productService.updateProduct(editProductDto,file);
        redirectAttributes.addFlashAttribute("success","Product update successfully");
       return "redirect:/admin/edit-product/"+editProductDto.getId();
    }

    @GetMapping("/delete-product/{id}")
    public String deleteProduct(@PathVariable Long id,RedirectAttributes redirectAttributes){
        productService.deleteProductById(id);
        redirectAttributes.addFlashAttribute("success","Product delete successfully");
        return "redirect:/admin/products";
    }

    @GetMapping("/search")
    public String search(Model model,@RequestParam String name){
        model.addAttribute("products",productService.findProductsContaining(name));
        return "admin/showProducts";
    }
}
