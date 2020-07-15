package ru.simbirSoft.dz1.service;

import org.springframework.jdbc.core.JdbcTemplate;

public interface ProductService {
    String addProductRandom(String nameProduct, JdbcTemplate jdbcTemplate);
}
