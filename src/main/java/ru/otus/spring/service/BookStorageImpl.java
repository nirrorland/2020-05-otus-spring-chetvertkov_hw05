package ru.otus.spring.service;


import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import ru.otus.spring.dao.AuthorDao;
import ru.otus.spring.dao.BookDao;
import ru.otus.spring.dao.GenreDao;
import ru.otus.spring.domain.Book;

@Service
public class BookStorageImpl implements BookStorage {
    private final AuthorDao authorRepository;
    private final GenreDao genreRepository;
    private final BookDao bookRepository;

    public BookStorageImpl(AuthorDao authorRepository, GenreDao genreRepository, BookDao bookRepository) {
        this.authorRepository = authorRepository;
        this.genreRepository = genreRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    public Mono<Book> update(Book updatedBook) {
        return Mono.just(updatedBook)
                .flatMap(book -> Mono.just(book.getAuthor())
                        .flatMap(author -> authorRepository.findByNameIgnoreCase(author.getName()))
                        .doOnNext(book::setAuthor).then(Mono.just(book)))

                .flatMap(book -> Mono.just(book.getGenre())
                        .flatMap(genre -> genreRepository.findByNameIgnoreCase(genre.getName()))
                        .doOnNext(book::setGenre).then(Mono.just(book)))

                .flatMap(bookRepository::save);
    }

}
