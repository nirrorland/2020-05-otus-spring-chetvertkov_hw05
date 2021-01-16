package ru.otus.spring.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.spring.domain.Author;
import java.util.Optional;

public interface AuthorDao extends ReactiveMongoRepository<Author, String> {

    Mono<Author> findByNameIgnoreCase(String name);

  //  Flux<Author> findAll();

}
