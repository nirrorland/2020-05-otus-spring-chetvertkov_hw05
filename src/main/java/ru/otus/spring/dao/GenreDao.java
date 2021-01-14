package ru.otus.spring.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;
import ru.otus.spring.domain.Genre;
import java.util.Optional;

public interface GenreDao extends ReactiveMongoRepository<Genre, String> {

    Mono<Genre> findById(long id);

    Mono<Genre> findByNameIgnoreCase(String name);
}
