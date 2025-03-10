package com.yakuash.crud.model.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductDto {

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
