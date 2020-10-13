package ru.otus.spring.dao;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.spring.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@SuppressWarnings({"SqlNoDataSourceInspection", "ConstantConditions", "SqlDialectInspection"})
@Repository
public class GenreDaoJdbc implements GenreDao {

    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    public GenreDaoJdbc(NamedParameterJdbcOperations namedParameterJdbcOperations) {
        this.namedParameterJdbcOperations = namedParameterJdbcOperations;
    }

    @Override
    public Genre getById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        return namedParameterJdbcOperations.queryForObject(
                "select * from genres where genre_id = :id", params, new GenreMapper()
        );
    }

    @Override
    public List<Genre> getAll() {
        String sql = "SELECT * FROM genres";
        List<Genre> orgList = namedParameterJdbcOperations.query(sql, new GenreMapper());

        return orgList;
    }

    @Override
    public Genre getByName(String name) {
        Map<String, Object> params = Collections.singletonMap("genre_name", name);
        try {
            return namedParameterJdbcOperations.queryForObject(
                    "select * from genres where lower(genre_name) = lower(:genre_name)", params, new GenreMapper()
            );
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    private static class GenreMapper implements RowMapper<Genre> {

        @Override
        public Genre mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getLong("genre_id");
            String name = resultSet.getString("genre_name");
            return new Genre(id, name);
        }
    }
}
