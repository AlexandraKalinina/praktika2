package ru.simbirSoft.dz1.repositories;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import ru.simbirSoft.dz1.model.Product;
import ru.simbirSoft.dz1.model.ProductCategory;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

public class ProductRepositoriesImpl implements ProductRepositories {

    //language=SQL
    private static final String SQL_SELECT_BY_ID = "select * from product where id = ?;";
    //language=SQL
    private static final String SQL_SELECT_ALL = "select * from product";
    //language=SQL
    private static final String SQL_INSERT = "insert into product(nameproduct,namecompany, id_category) values (?,?,?)";
    //language=SQL
    private static final String SQL_DELETE_BY_ID = "delete from product where id=?;";
    //language=SQL
    private static final String SQL_SELECT_ALL_AND_CATEGORY = "select product.id, nameproduct, namecategory from product left join category c on product.id_category = c.id";

    private JdbcTemplate jdbcTemplate;

    private CategoryRepositories categoryRepositories;

    public ProductRepositoriesImpl(JdbcTemplate jdbcTemplate, CategoryRepositories categoryRepositories) {
        this.jdbcTemplate = jdbcTemplate;
        this.categoryRepositories = categoryRepositories;
    }
    public ProductRepositoriesImpl() {}

    public ProductRepositoriesImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private RowMapper<Product> productRowMapper = (row, rowNumber) ->
            Product.builder()
                    .id(row.getLong("id"))
                    .nameProduct(row.getString("nameProduct"))
                    .nameCompany(row.getString("nameCompany"))
                    .category(categoryRepositories.find(row.getLong("id_category")).get())
                    .build();

    @Override
    public Optional<Product> find(Long id) {
        try {
            Product product = jdbcTemplate.queryForObject(SQL_SELECT_BY_ID, new Object[]{id}, productRowMapper);
            return Optional.ofNullable(product);
        } catch (
                EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public void save(Product object) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection
                    .prepareStatement(SQL_INSERT);
            statement.setString(1, object.getNameProduct());
            statement.setString(2, object.getNameCompany());
            statement.setLong(3, object.getCategory().getId());
            return statement;
        }, keyHolder);
        object.setId((Long)keyHolder.getKey());
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update(SQL_DELETE_BY_ID,id);
    }

    @Override
    public List<Product> findAll() {
        return jdbcTemplate.query(SQL_SELECT_ALL, productRowMapper);
    }

    private RowMapper<ProductCategory> productCategoryRowMapper = (row, rowNumber) ->
            ProductCategory.builder()
                    .id(row.getLong("id"))
                    .nameProduct(row.getString("nameProduct"))
                    .nameCategory(row.getString("nameCategory"))
                    .build();
    @Override
    public List<ProductCategory> findAllAndCategory() {
        return jdbcTemplate.query(SQL_SELECT_ALL_AND_CATEGORY, productCategoryRowMapper);
    }
}
