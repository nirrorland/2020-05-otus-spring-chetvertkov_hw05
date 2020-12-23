package ru.otus.spring.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.spring.domain.Book;
import java.util.Optional;

public interface BookDao extends MongoRepository<Book, String> {

    Optional<Book> findByNameIgnoreCase(String name);

}
