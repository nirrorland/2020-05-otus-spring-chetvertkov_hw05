package ru.otus.spring.web.handlers;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.spring.dao.BookDao;
import ru.otus.spring.service.BookStorage;
import ru.otus.spring.web.dto.BookDto;
import ru.otus.spring.web.dto.CommentDto;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.BodyInserters.fromValue;
import static org.springframework.web.reactive.function.server.ServerResponse.notFound;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
public class BookHandlerImpl implements BookHandler {

    private final BookDao bookRepository;
    private final BookStorage bookStorage;


    public BookHandlerImpl(BookDao bookRepository, BookStorage bookStorage) {
        this.bookRepository = bookRepository;
        this.bookStorage = bookStorage;
    }

    @Override
    public Mono<ServerResponse> getAllBooks(ServerRequest request) {
        return ok().body(bookRepository.findAll().map(BookDto::toDto), BookDao.class);
    }

    @Override
    public Mono<ServerResponse> addBook(ServerRequest request) {
        return request.bodyToMono(BookDto.class)
                .flatMap(bookDto -> bookStorage.update(BookDto.toDomain(bookDto)))
                .flatMap(book -> ok()
                        .contentType(APPLICATION_JSON)
                        .body(fromValue(book)));
    }

    @Override
    public Mono<ServerResponse> getBookById(ServerRequest request) {
        return bookRepository.findById(request.pathVariable("id"))
                .flatMap(book -> ok().contentType(APPLICATION_JSON).bodyValue(BookDto.toDto(book)))
                .switchIfEmpty(notFound().build());
    }

    @Override
    public Mono<ServerResponse> updateBook(ServerRequest request) {
        return request.bodyToMono(BookDto.class)
                .flatMap(bookDto -> bookStorage.update(BookDto.toDomain(bookDto)))
                .flatMap(book -> ok()
                        .contentType(APPLICATION_JSON)
                        .body(fromValue(book)));
    }

    @Override
    public Mono<ServerResponse> deleteBook(ServerRequest request) {
        return bookRepository.deleteById(request.pathVariable("id"))
                .flatMap(book -> ok().contentType(APPLICATION_JSON).build());
    }

    @Override
    public Mono<ServerResponse> getCommentsForBook(ServerRequest request) {
        return ok().body(
                 bookRepository.findById(request.pathVariable("id"))
                .flatMap(book -> Mono.just(book.getComments()))
                .flatMapMany(Flux::fromIterable)
                .map(CommentDto::toDto), CommentDto.class
        )
;
    }
}
