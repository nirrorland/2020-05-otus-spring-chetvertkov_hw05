package ru.otus.spring.service;

import reactor.core.publisher.Mono;
import ru.otus.spring.domain.Book;

public interface BookStorage {
    Mono<Book> update(Book updatedBook);
}
