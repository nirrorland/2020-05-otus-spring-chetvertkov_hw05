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
class GenreHandlerTest {

    @Autowired
    private WebTestClient client;

    @Test
    void getAllGenresTest() {

        client.get().uri("/api/genre")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$[0].genreName").isEqualTo("Drama")
                .jsonPath("$[0].id").isNotEmpty()

                .jsonPath("$[1].genreName").isEqualTo("Comics")
                .jsonPath("$[1].id").isNotEmpty()

                .jsonPath("$[2].genreName").isEqualTo("History")
                .jsonPath("$[2].id").isNotEmpty();

    }
}