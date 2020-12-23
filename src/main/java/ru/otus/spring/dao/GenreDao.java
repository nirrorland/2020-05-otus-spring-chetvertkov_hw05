package ru.otus.spring.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.spring.domain.Genre;
import java.util.Optional;

public interface GenreDao extends MongoRepository<Genre, String> {

    Optional<Genre> findById(long id);

    Optional<Genre> findByNameIgnoreCase(String name);
}
