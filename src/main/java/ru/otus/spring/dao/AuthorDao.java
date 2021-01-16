package ru.otus.spring.dao;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;
import ru.otus.spring.domain.Author;

public interface AuthorDao extends ReactiveMongoRepository<Author, String> {

    Mono<Author> findByNameIgnoreCase(String name);

}
