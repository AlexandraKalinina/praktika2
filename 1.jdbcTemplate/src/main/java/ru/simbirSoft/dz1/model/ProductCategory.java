package ru.simbirSoft.dz1.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductCategory {
    private Long id;
    private String nameProduct;
    private String nameCategory;
}
