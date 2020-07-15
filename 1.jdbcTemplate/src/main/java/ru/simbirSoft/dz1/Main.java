package ru.simbirSoft.dz1;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.simbirSoft.dz1.model.Product;
import ru.simbirSoft.dz1.model.ProductCategory;
import ru.simbirSoft.dz1.repositories.CategoryRepositories;
import ru.simbirSoft.dz1.repositories.CategoryRepositoriesImpl;
import ru.simbirSoft.dz1.repositories.ProductRepositories;
import ru.simbirSoft.dz1.repositories.ProductRepositoriesImpl;
import ru.simbirSoft.dz1.service.ProductService;
import ru.simbirSoft.dz1.service.ProductServiceImpl;

import java.util.List;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        HikariConfig config = new HikariConfig();
        config.setUsername("postgres");
        config.setPassword("123");
        config.setJdbcUrl("jdbc:postgresql://localhost:5432/ProductsSimbir");
        HikariDataSource dataSource = new HikariDataSource(config);
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        CategoryRepositories categoryRepositories = new CategoryRepositoriesImpl(jdbcTemplate);
        ProductRepositories productRepositories = new ProductRepositoriesImpl(jdbcTemplate,categoryRepositories);
        /*List<ProductCategory> products = productRepositories.findAllAndCategory();
        System.out.println(products);*/
        ProductService productService = new ProductServiceImpl();
        String nameCategory = productService.addProductRandom("apple", jdbcTemplate);
        System.out.println(nameCategory);
    }
}
