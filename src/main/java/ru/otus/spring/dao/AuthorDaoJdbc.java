package ru.otus.spring.dao;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.spring.domain.Author;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@SuppressWarnings({"SqlNoDataSourceInspection", "ConstantConditions", "SqlDialectInspection"})
@Repository
public class AuthorDaoJdbc implements AuthorDao {

    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    public AuthorDaoJdbc(NamedParameterJdbcOperations namedParameterJdbcOperations) {
        this.namedParameterJdbcOperations = namedParameterJdbcOperations;
    }

    @Override
    public Author getById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        return namedParameterJdbcOperations.queryForObject(
                "select * from authors where author_id = :id", params, new AuthorMapper()
        );
    }

    @Override
    public List<Author> getAll() {
        String sql = "SELECT * FROM authors";
        List<Author> orgList = namedParameterJdbcOperations.query(sql, new AuthorMapper());

        return orgList;
    }

    @Override
    public Author getByName(String name) {
        Map<String, Object> params = Collections.singletonMap("author_name", name);
        try {
            return namedParameterJdbcOperations.queryForObject(
                    "select * from authors where lower(author_name) = lower(:author_name)", params, new AuthorMapper()
            );
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }


    private static class AuthorMapper implements RowMapper<Author> {

        @Override
        public Author mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getLong("author_id");
            String name = resultSet.getString("author_name");
            return new Author(id, name);
        }
    }
}
