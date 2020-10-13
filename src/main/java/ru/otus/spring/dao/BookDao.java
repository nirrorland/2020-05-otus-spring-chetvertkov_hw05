package ru.otus.spring.dao;

import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;

import java.util.List;

public interface BookDao {

    Book getById(long id);

    List<Book> getAll();

    Book getByName(String name);

    void insert(Book book);

    void update(Book book);

    void deleteById(long id);
}
