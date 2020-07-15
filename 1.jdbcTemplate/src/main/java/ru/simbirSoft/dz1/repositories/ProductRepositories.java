package ru.simbirSoft.dz1.repositories;

import ru.simbirSoft.dz1.model.Product;
import ru.simbirSoft.dz1.model.ProductCategory;

import java.util.List;

public interface ProductRepositories extends CrudRepositories<Product, Long> {
    List<ProductCategory> findAllAndCategory();
}
