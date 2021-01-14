package ru.otus.spring.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;
import ru.otus.spring.domain.Book;
import java.util.Optional;

public interface BookDao extends ReactiveMongoRepository<Book, String> {

    Mono<Book> findByNameIgnoreCase(String name);

}
