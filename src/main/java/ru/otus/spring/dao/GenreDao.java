package ru.otus.spring.dao;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;
import ru.otus.spring.domain.Genre;

public interface GenreDao extends ReactiveMongoRepository<Genre, String> {

    Mono<Genre> findByNameIgnoreCase(String name);
}
