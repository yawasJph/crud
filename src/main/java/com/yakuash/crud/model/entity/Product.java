package com.yakuash.crud.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "products")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 500)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    private Double price;
    private int stock;
    private String image;
    private int discount;
    private Double discountPrice;
    private Boolean isActive;

    @ManyToOne(fetch = FetchType.EAGER)
    private Category category;
}
