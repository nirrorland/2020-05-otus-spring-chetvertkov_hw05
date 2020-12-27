package ru.otus.spring.service;

import ru.otus.spring.domain.Book;

import java.util.List;

public interface BookService {

    Book getById(String id);

    Book getByName(String name);

    List<Book> getAll();

    void insert(Book book);

    void update(Book book);

    void deleteById(String id);

    void deleteCommentById(String id);
}
