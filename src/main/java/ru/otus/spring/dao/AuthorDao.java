package ru.otus.spring.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.spring.domain.Author;
import java.util.Optional;

public interface AuthorDao  extends MongoRepository<Author, String> {

    Optional<Author> findByNameIgnoreCase(String name);

}
