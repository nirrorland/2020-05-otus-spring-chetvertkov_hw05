package ru.otus.spring.web;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import ru.otus.spring.dao.AuthorDao;
import ru.otus.spring.dao.BookDao;
import ru.otus.spring.dao.GenreDao;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Genre;
import ru.otus.spring.web.dto.AuthorDto;
import ru.otus.spring.web.dto.BookDto;
import ru.otus.spring.web.dto.GenreDto;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.BodyExtractors.toMono;
import static org.springframework.web.reactive.function.BodyInserters.fromValue;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.created;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Configuration
public class Router {
    @Bean
    public RouterFunction<ServerResponse> composedRoutes(AuthorDao authorRepository, GenreDao genreRepository, BookDao bookRepository) {
        return route()
//                .GET("/func/person", queryParam("name", StringUtils::isNotEmpty),
//                        request -> request.queryParam("name")
//                                .map(authorRepository::findAll)
//                                .map(persons -> ok().body(persons, Author.class))
//                                .orElse(notFound().build())
//                )


                .GET("/api/author", accept(APPLICATION_JSON),
                        request -> ok().contentType(APPLICATION_JSON).body(authorRepository.findAll().map(AuthorDto::toDto), Author.class))

                .GET("/api/genre", accept(APPLICATION_JSON),
                        request -> ok().contentType(APPLICATION_JSON).body(genreRepository.findAll().map(GenreDto::toDto), Genre.class))

                .GET("/api/book", accept(APPLICATION_JSON),
                        request -> ok().contentType(APPLICATION_JSON).body(bookRepository.findAll().map(BookDto::toDto), Book.class))

                .POST("/api/book", accept(APPLICATION_JSON),
                        request -> request.bodyToMono(BookDto.class)
                                .flatMap(bookDto -> bookRepository.save(BookDto.toDomain(bookDto)))
                                .flatMap(book -> ok().contentType(APPLICATION_JSON).build());



//                .GET("/func/person/{id}", accept(APPLICATION_JSON),
//                        request -> repository.findById(request.pathVariable("id"))
//                                .flatMap(person -> ok().contentType(APPLICATION_JSON).body(fromObject(person)))
//                )

    }
}
