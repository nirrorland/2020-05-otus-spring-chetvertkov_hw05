package ru.otus.spring.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest
@AutoConfigureWebTestClient
@DirtiesContext
class AuthorHandlerTest {

    @Autowired
    private WebTestClient client;

    @Test
    void getAllAuthorsTest() {

        client.get().uri("/api/author")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$[0].authorName").isEqualTo("Tolkiena")
                .jsonPath("$[0].id").isNotEmpty()

                .jsonPath("$[1].authorName").isEqualTo("Machiavelli")
                .jsonPath("$[1].id").isNotEmpty()

                .jsonPath("$[2].authorName").isEqualTo("Tolstoy")
                .jsonPath("$[2].id").isNotEmpty();

    }
}