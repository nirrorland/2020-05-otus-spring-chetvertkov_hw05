package ru.otus.spring.web.handlers;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import ru.otus.spring.dao.AuthorDao;
import ru.otus.spring.web.dto.AuthorDto;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
public class AuthorHandlerImpl implements AuthorHandler{

    private final AuthorDao authorRepository;

    public AuthorHandlerImpl(AuthorDao authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public Mono<ServerResponse> getAllAuthors(ServerRequest request) {
        return ok().body(authorRepository.findAll().map(AuthorDto::toDto), AuthorDto.class);
    }
}
