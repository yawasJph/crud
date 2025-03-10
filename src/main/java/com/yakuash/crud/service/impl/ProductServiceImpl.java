package com.yakuash.crud.service.impl;

import com.yakuash.crud.model.dto.EditProductDto;
import com.yakuash.crud.model.dto.ProductDto;
import com.yakuash.crud.model.entity.Category;
import com.yakuash.crud.model.entity.Product;
import com.yakuash.crud.repository.CategoryRepository;
import com.yakuash.crud.repository.ProductRepository;
import com.yakuash.crud.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final Logger LOGGER = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Override
    public void save(ProductDto productDto) {
        Product product = new Product();
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setStock(productDto.getStock());
        product.setPrice(productDto.getPrice());
        product.setDiscount(productDto.getDiscount());
        Double discountPrice = productDto.getPrice() * (productDto.getDiscount()/100.0);
        product.setDiscountPrice(productDto.getPrice() - discountPrice);
        product.setIsActive(productDto.getIsActive());
        product.setImage(productDto.getImage());

        List<Category> categories = categoryRepository.findAll();
        for (Category c : categories) {
            if (c.getName().equals(productDto.getCategory())) {
                product.setCategory(c);
            }
        }
       productRepository.save(product);
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
    public List<Product> getAll() {
        return productRepository.findAll();
    }

    @Override
    public Product findById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public void updateProduct(EditProductDto editProductDto,MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            Product oldProduct = productRepository.findById(editProductDto.getId()).orElse(null);
            if (oldProduct != null) {
                editProductDto.setImage(oldProduct.getImage());
            }
            Product product = new Product();
            product.setId(editProductDto.getId());
            product.setDescription(editProductDto.getDescription());
            product.setName(editProductDto.getName());
            product.setStock(editProductDto.getStock());
            product.setIsActive(editProductDto.getIsActive());
            product.setDiscount(editProductDto.getDiscount());
            product.setPrice(editProductDto.getPrice());
            Double discountPrice = editProductDto.getPrice() * (editProductDto.getDiscount() / 100.0);
            product.setDiscountPrice(editProductDto.getPrice() - discountPrice);
            product.setImage(editProductDto.getImage());

            List<Category> categories = categoryRepository.findAll();
            for (Category c : categories) {
                if (c.getName().equals(editProductDto.getCategory())) {
                    product.setCategory(c);
                }
            }
            productRepository.save(product);
            return;
        }
            String image = saveImage(file);
            editProductDto.setImage(image);
            Product product = new Product();
            product.setId(editProductDto.getId());
            product.setStock(editProductDto.getStock());
            product.setDescription(editProductDto.getDescription());
            product.setName(editProductDto.getName());
            product.setIsActive(editProductDto.getIsActive());
            product.setDiscount(editProductDto.getDiscount());
            product.setPrice(editProductDto.getPrice());
            Double discountPrice = editProductDto.getPrice() * (editProductDto.getDiscount() / 100.0);
            product.setDiscountPrice(editProductDto.getPrice() - discountPrice);
            product.setImage(editProductDto.getImage());

            List<Category> categories = categoryRepository.findAll();
            for (Category c : categories) {
                if (c.getName().equals(editProductDto.getCategory())) {
                    product.setCategory(c);
                }
            }
            productRepository.save(product);
        }

    @Override
    public Boolean existsProduct(String name) {
        Boolean existsProduct = productRepository.existsProductByName(name);
        if (existsProduct){
            return true;
        }
        return false;
    }

    @Override
    public List<Product> findProductsContaining(String name) {
        return productRepository.findByNameContainingIgnoreCase(name);
    }

    @Override
    public void deleteProductById(Long id) {
        productRepository.deleteById(id);
    }
}
