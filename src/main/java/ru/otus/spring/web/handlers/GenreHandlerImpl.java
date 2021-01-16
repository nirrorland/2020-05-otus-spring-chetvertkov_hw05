package ru.otus.spring.web.handlers;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import ru.otus.spring.dao.GenreDao;
import ru.otus.spring.web.dto.GenreDto;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
public class GenreHandlerImpl implements GenreHandler{

    private final GenreDao genreRepository;

    public GenreHandlerImpl(GenreDao genreRepository) {
        this.genreRepository = genreRepository;
    }

    @Override
    public Mono<ServerResponse> getAllGenres(ServerRequest request) {
        return ok().body(genreRepository.findAll().map(GenreDto::toDto), GenreDto.class);
    }
}
