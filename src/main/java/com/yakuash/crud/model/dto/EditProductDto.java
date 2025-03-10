package com.yakuash.crud.model.dto;

import lombok.Data;

@Data
public class EditProductDto {
    private Long id;
    private String name;
    private String description;
    private Double price;
    private Integer stock;
    private Integer discount;
    private String category;
    private String image;
    private Double discountPrice;
    private Boolean isActive;
}
