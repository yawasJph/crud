package com.yakuash.crud.repository;

import com.yakuash.crud.model.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long>, PagingAndSortingRepository<Product,Long> {

    Boolean existsProductByName(String name);
    List<Product> findByNameContainingIgnoreCase(String name);
}
