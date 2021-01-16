package ru.otus.spring.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import ru.otus.spring.web.handlers.AuthorHandler;
import ru.otus.spring.web.handlers.BookHandler;
import ru.otus.spring.web.handlers.GenreHandler;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Configuration
public class Router {
    @Bean
    RouterFunction<ServerResponse> routes (AuthorHandler authorHandler, GenreHandler genreHandler, BookHandler bookHandler) {
        return RouterFunctions
                .route(GET("/api/genre").and(accept(MediaType.APPLICATION_JSON)), genreHandler::getAllGenres)
                .andRoute(GET("/api/author").and(accept(MediaType.APPLICATION_JSON)), authorHandler::getAllAuthors)
                .andRoute(GET("/api/book").and(accept(MediaType.APPLICATION_JSON)), bookHandler::getAllBooks)
                .andRoute(POST("/api/book").and(accept(MediaType.APPLICATION_JSON)), bookHandler::addBook)
                .andRoute(GET("/api/book/{id}").and(accept(MediaType.APPLICATION_JSON)), bookHandler::getBookById)
                .andRoute(PUT("/api/book").and(accept(MediaType.APPLICATION_JSON)), bookHandler::updateBook)
                .andRoute(DELETE("/api/book/{id}").and(accept(MediaType.APPLICATION_JSON)), bookHandler::deleteBook)
                .andRoute(GET("/api/book/{id}/comments").and(accept(MediaType.APPLICATION_JSON)), bookHandler::getCommentsForBook);
    }
}
