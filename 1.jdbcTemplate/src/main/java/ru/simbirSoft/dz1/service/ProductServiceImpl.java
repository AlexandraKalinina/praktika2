package ru.simbirSoft.dz1.service;

import org.springframework.jdbc.core.JdbcTemplate;
import ru.simbirSoft.dz1.model.Category;
import ru.simbirSoft.dz1.model.Product;
import ru.simbirSoft.dz1.repositories.CategoryRepositories;
import ru.simbirSoft.dz1.repositories.CategoryRepositoriesImpl;
import ru.simbirSoft.dz1.repositories.ProductRepositories;
import ru.simbirSoft.dz1.repositories.ProductRepositoriesImpl;

import java.util.List;
import java.util.Random;

public class ProductServiceImpl implements ProductService {
    @Override
    public String addProductRandom(String nameProduct, JdbcTemplate jdbcTemplate) {
        CategoryRepositories categoryRepositories = new CategoryRepositoriesImpl(jdbcTemplate);
        ProductRepositories productRepositories = new ProductRepositoriesImpl(jdbcTemplate);
        List<Category> categories = categoryRepositories.findAll();
        int max = categories.size();
        int min = 1;
        Random random = new Random();
        int randomCategory = random.nextInt(max - min + 1);
        Category category = categoryRepositories.find((long) randomCategory).get();
        Product product = Product.builder()
                .nameProduct(nameProduct)
                .nameCompany("name")
                .category(category)
                .build();
        productRepositories.save(product);
        return category.getNameCategory();
    }
}
