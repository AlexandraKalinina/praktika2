package ru.simbirSoft.dz1.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.simbirSoft.dz1.model.Category;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {
    private Long id;
    private String nameProduct;
    private String nameCompany;
    private Category category;
}
