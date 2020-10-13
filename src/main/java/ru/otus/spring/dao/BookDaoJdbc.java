package ru.otus.spring.dao;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings({"SqlNoDataSourceInspection", "ConstantConditions", "SqlDialectInspection"})
@Repository
public class BookDaoJdbc implements BookDao {

    private final NamedParameterJdbcOperations namedParameterJdbcOperations;
    private final AuthorDao authorRepository;
    private final GenreDao genreRepository;


    public BookDaoJdbc(NamedParameterJdbcOperations namedParameterJdbcOperations, AuthorDao authorDao, GenreDao genreDao) {
        this.namedParameterJdbcOperations = namedParameterJdbcOperations;
        this.authorRepository = authorDao;
        this.genreRepository = genreDao;
    }

    @Override
    public Book getById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        String sql = "SELECT * FROM books b " +
                "INNER JOIN authors a on b.author_id = a.author_id " +
                "INNER JOIN genres g on b.genre_id = g.genre_id " +
                "WHERE b.book_id = :id";
        try {
            return namedParameterJdbcOperations.queryForObject(
                    sql, params, new BookMapper()
            );
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }


    @Override
    public List<Book> getAll() {
        String sql = "SELECT * FROM books b " +
                "INNER JOIN authors a on b.author_id = a.author_id " +
                "INNER JOIN genres g on b.genre_id = g.genre_id";
        List<Book> bookList = namedParameterJdbcOperations.query(sql, new BookMapper());

        return bookList;
    }

    @Override
    public Book getByName(String name) {
        String sql = "SELECT * FROM books b " +
                "INNER JOIN authors a on b.author_id = a.author_id " +
                "INNER JOIN genres g on b.genre_id = g.genre_id " +
                "WHERE lower(b.book_name) = lower(:book_name)";
        Map<String, Object> params = Collections.singletonMap("book_name", name);
        try {
            return namedParameterJdbcOperations.queryForObject(sql, params, new BookMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public void insert(Book book) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("BOOK_NAME", book.getName());
        params.put("AUTHOR_ID", book.getAuthor().getId());
        params.put("GENRE_ID", book.getGenre().getId());
        namedParameterJdbcOperations.update("INSERT INTO BOOKS (BOOK_NAME, AUTHOR_ID, GENRE_ID) VALUES ( :BOOK_NAME, :AUTHOR_ID, :GENRE_ID )", params);
    }

    @Override
    public void update(Book book) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("BOOK_ID", book.getId());
        params.put("BOOK_NAME", book.getName());
        params.put("AUTHOR_ID", book.getAuthor().getId());
        params.put("GENRE_ID", book.getGenre().getId());
        String sql = "UPDATE BOOKS " +
                "  SET " +
                "      BOOK_NAME = :BOOK_NAME," +
                "      AUTHOR_ID = :AUTHOR_ID," +
                "      GENRE_ID = :GENRE_ID" +
                "   WHERE BOOK_ID = :BOOK_ID";

        namedParameterJdbcOperations.update(sql, params);
    }

    @Override
    public void deleteById(long id) {
        Map<String, Object> params = Collections.singletonMap("BOOK_ID", id);
        String sql = "DELETE BOOKS " +
                "WHERE BOOK_ID = :BOOK_ID";

        namedParameterJdbcOperations.update(sql, params);
    }

    private static class BookMapper implements RowMapper<Book> {

        @Override
        public Book mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getLong("book_id");
            String name = resultSet.getString("book_name");
            Author author = new Author(resultSet.getLong("author_id"), resultSet.getString("author_name"));
            Genre genre = new Genre(resultSet.getLong("genre_id"), resultSet.getString("genre_name"));
            return new Book(id, name, author, genre);
        }
    }
}
