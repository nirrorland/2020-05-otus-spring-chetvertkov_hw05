package ru.otus.spring.service;

import ru.otus.spring.domain.Author;

import java.util.List;

public interface AuthorService {

    Author getById(String id);

    List<Author> getAll();

    Author getByName(String name);
}
