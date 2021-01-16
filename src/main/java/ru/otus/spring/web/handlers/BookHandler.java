package ru.otus.spring.web.handlers;

import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

public interface BookHandler {
    Mono<ServerResponse> getAllBooks(ServerRequest request);

    Mono<ServerResponse> addBook(ServerRequest request);

    Mono<ServerResponse> getBookById(ServerRequest request);

    Mono<ServerResponse> updateBook(ServerRequest request);

    Mono<ServerResponse> deleteBook(ServerRequest request);

    Mono<ServerResponse> getCommentsForBook(ServerRequest request);

}
