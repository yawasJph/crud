package com.yakuash.crud.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EditCategoryDto {

    private Long id;
    private String name;
    private String image;
    private Boolean isActive;
}
