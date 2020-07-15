package ru.simbirSoft.dz1.repositories;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import ru.simbirSoft.dz1.model.Category;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

public class CategoryRepositoriesImpl implements CategoryRepositories {
    //language=SQL
    private static final String SQL_SELECT_BY_ID = "select * from category where id = ?;";
    //language=SQL
    private static final String SQL_SELECT_ALL = "select * from category";
    //language=SQL
    private static final String SQL_INSERT = "insert into category(nameCategory) values (?)";
    //language=SQL
    private static final String SQL_DELETE_BY_ID = "delete from category where id=?;";

    private JdbcTemplate jdbcTemplate;

    private RowMapper<Category > categoryRowMapper = (row, rowNumber) ->
            Category.builder()
                    .id(row.getLong("id"))
                    .nameCategory(row.getString("nameCategory"))
                    .build();

    public CategoryRepositoriesImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    public CategoryRepositoriesImpl() {}

    @Override
    public Optional<Category> find(Long id) {
        try {
            Category category = jdbcTemplate.queryForObject(SQL_SELECT_BY_ID, new Object[]{id}, categoryRowMapper);
            return Optional.ofNullable(category);
        } catch (
                EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public void save(Category object) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection
                    .prepareStatement(SQL_INSERT);
            statement.setString(1, object.getNameCategory());

            return statement;
        }, keyHolder);
        object.setId((Long)keyHolder.getKey());
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update(SQL_DELETE_BY_ID,id);
    }

    @Override
    public List<Category> findAll() {
        return jdbcTemplate.query(SQL_SELECT_ALL, categoryRowMapper);
    }
}
