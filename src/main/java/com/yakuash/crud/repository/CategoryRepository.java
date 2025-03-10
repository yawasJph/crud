package com.yakuash.crud.repository;

import com.yakuash.crud.model.entity.Category;
import com.yakuash.crud.model.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long>, PagingAndSortingRepository<Category,Long> {

    Boolean existsCategoryByName(String name);
    List<Product> findByNameContainingIgnoreCase(String name);
}
