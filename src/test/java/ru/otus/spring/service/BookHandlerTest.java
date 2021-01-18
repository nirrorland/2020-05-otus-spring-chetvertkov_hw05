package ru.otus.spring.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.reactive.server.WebTestClient;
import ru.otus.spring.dao.BookDao;
import ru.otus.spring.domain.Book;

@SpringBootTest
@AutoConfigureWebTestClient
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class BookHandlerTest {

    @Autowired
    private WebTestClient client;

    @Autowired
    BookDao bookRepository;

    @Test
    void getAllBooksTest() {

        client.get().uri("/api/book")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$[0].id").isNotEmpty()
                .jsonPath("$[0].name").isEqualTo("Lord of the Rings")
                .jsonPath("$[0].author").isEqualTo("Tolkiena")
                .jsonPath("$[0].genre").isEqualTo("Drama")

                .jsonPath("$[1].id").isNotEmpty()
                .jsonPath("$[1].name").isEqualTo("Istorie Florentine")
                .jsonPath("$[1].author").isEqualTo("Machiavelli")
                .jsonPath("$[1].genre").isEqualTo("History")

                .jsonPath("$[2].id").isNotEmpty()
                .jsonPath("$[2].name").isEqualTo("Martian")
                .jsonPath("$[2].author").isEqualTo("Tolstoy")
                .jsonPath("$[2].genre").isEqualTo("Comics");

    }

    @Test
    void getBookByIdTest() {
        String id = bookRepository.findByNameIgnoreCase("Martian").block().getId();

        client.get().uri("/api/book/{id}", id)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isNotEmpty()
                .jsonPath("$.name").isEqualTo("Martian")
                .jsonPath("$.author").isEqualTo("Tolstoy")
                .jsonPath("$.genre").isEqualTo("Comics");
    }

    @Test
    void addBookTest() {
        assert bookRepository.findAll().collectList().block().size() == 3;

        //{"name":"Test1","author":"Tolstoy","genre":"Comics"}

        client.post().uri("/api/book/")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("{\"name\":\"Test1\",\"author\":\"Tolstoy\",\"genre\":\"Comics\"}")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isNotEmpty()
                .jsonPath("$.name").isEqualTo("Test1")
                .jsonPath("$.author.id").isNotEmpty()
                .jsonPath("$.author.name").isEqualTo("Tolstoy")
                .jsonPath("$.genre.id").isNotEmpty()
                .jsonPath("$.genre.name").isEqualTo("Comics");

        assert bookRepository.findAll().collectList().block().size() == 4;
    }

    @Test
    void updateBookTest() {
        String id = bookRepository.findByNameIgnoreCase("Martian").block().getId();
        assert bookRepository.findAll().collectList().block().size() == 3;

        //{"id":"6003238d17eab23225c7948e","name":"Florentine Istorie","author":"Tolstoy","genre":"Comics"}

        client.put().uri("/api/book/")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("{\"id\":\"" + id + "\",\"name\":\"Florentine Istorie\",\"author\":\"Tolstoy\",\"genre\":\"Comics\"}")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isNotEmpty()
                .jsonPath("$.name").isEqualTo("Florentine Istorie")
                .jsonPath("$.author.id").isNotEmpty()
                .jsonPath("$.author.name").isEqualTo("Tolstoy")
                .jsonPath("$.genre.id").isNotEmpty()
                .jsonPath("$.genre.name").isEqualTo("Comics");

        assert bookRepository.findAll().collectList().block().size() == 3;
    }

    @Test
    void deleteBookTest() {
        String id = bookRepository.findByNameIgnoreCase("Martian").block().getId();
        assert bookRepository.findAll().collectList().block().size() == 3;

        client.delete().uri("/api/book/{id}", id)
                .exchange()
                .expectStatus().isOk()
                .expectBody().isEmpty();

        assert bookRepository.findAll().collectList().block().size() == 2;
    }

    @Test
    void getCommentsForBookTest() {
        String id = bookRepository.findByNameIgnoreCase("Martian").block().getId();

        client.get().uri("/api/book/{id}/comments/", id)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$[0].id").isNotEmpty()
                .jsonPath("$[0].text").isEqualTo("Martian Comment 1")
                .jsonPath("$[1].id").isNotEmpty()
                .jsonPath("$[1].text").isEqualTo("Martian Comment 2");;
    }

}