package com.yakuash.crud.service;

import com.yakuash.crud.model.dto.EditProductDto;
import com.yakuash.crud.model.dto.ProductDto;
import com.yakuash.crud.model.entity.Product;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProductService {

    void save(ProductDto productDto);
    String saveImage(MultipartFile file) throws IOException;
    List<Product> getAll();
    Product findById(Long id);
    void updateProduct(EditProductDto editProductDto,MultipartFile file) throws IOException;
    Boolean existsProduct(String name);
    List<Product> findProductsContaining(String name);
    void deleteProductById(Long id);
}
