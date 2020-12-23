package ru.otus.spring.service;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;
import org.springframework.test.annotation.DirtiesContext;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Comment;
import ru.otus.spring.domain.Genre;
import ru.otus.spring.event.ErrorEvent;
import ru.otus.spring.event.EventListner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest(properties = {
        InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false",
        ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT + ".enabled=false"
})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class BookStorageImplTest {

    @Autowired
    BookService bookService;

    @Autowired
    CommentService commentService;

    @MockBean
    EventListner eventListner;

    @Autowired
    BookStorage bookStorageService;

    @Test
    void getAllAuthorsTest() {
        List<Author> authors = bookStorageService.getAllAuthors();

        assertEquals(authors.size(), 3);
        assertEquals(authors.get(0).getName(), "Tolkiena");
        assertEquals(authors.get(1).getName(), "Machiavelli");
        assertEquals(authors.get(2).getName(), "Tolstoy");
    }

    @Test
    void getAllGenresTest() {
        List<Genre> genres = bookStorageService.getAllGenres();

        assertEquals(genres.size(), 3);
        assertEquals(genres.get(0).getName(), "Drama");
        assertEquals(genres.get(1).getName(), "Comics");
        assertEquals(genres.get(2).getName(), "History");
    }

    @Test
    void getAllBooksTest() {
        List<Book> books = bookStorageService.getAllBooks();

        assertEquals(books.size(), 3);
        assertEquals(books.get(0).getName(), "Lord of the Rings");
        assertEquals(books.get(1).getName(), "Istorie Florentine");
        assertEquals(books.get(2).getName(), "Martian");
    }

    @Test
    void getCommentsForBookTest() {
        List<Comment> comments = bookStorageService.getCommentsForBook("Lord of the Rings");
        assertEquals(comments.size(), 0);

        bookStorageService.addComment("Lord of the Rings", "TestComment2");

        List<Comment> resultComments = bookStorageService.getCommentsForBook("Lord of the Rings");
        assertEquals(resultComments.get(0).getText(), "TestComment2");
    }

    @Test
    @Order(5)
    void insertBookTestWhenNoGenre() {
        bookStorageService.insertBook("123", "Tolstoy", "Drama22");

        assertNull(bookService.getByName("123"));
        verify(eventListner).handleErrorListener(any(ErrorEvent.class));
    }

    @Test
    @Order(6)
    void insertBookTestWhenNoGenreAndNoAuthor() {
        bookStorageService.insertBook("123", "Tolstoy22", "Drama22");

        assertNull(bookService.getByName("123"));
        verify(eventListner, times(2)).handleErrorListener(any(ErrorEvent.class));
    }

    @Test
    @Order(7)
    void insertBookTestWhenNoAuthor() {
        bookStorageService.insertBook("123", "Tolstoy22", "Drama");

        assertNull(bookService.getByName("123"));
        verify(eventListner).handleErrorListener(any(ErrorEvent.class));
    }



    @Test
    @Order(8)
    void updateBookTestNoAuthor() {
        bookStorageService.updateBook("Martian", "NewMartian", "Tolstoy22", "Drama");

        assertNull(bookService.getByName("NewMartian"));
        verify(eventListner).handleErrorListener(any(ErrorEvent.class));
    }

    @Test
    @Order(9)
    void updateBookTestNoGenre() {
        bookStorageService.updateBook("Martian", "NewMartian", "Tolstoy", "Drama22");

        assertNull(bookService.getByName("NewMartian"));
        verify(eventListner).handleErrorListener(any(ErrorEvent.class));
    }

    @Test
    @Order(10)
    void updateBookTestNoAuthorAndNoGenre() {
        bookStorageService.updateBook("Martian", "NewMartian", "Tolstoy22", "Drama22");

        assertNull(bookService.getByName("NewMartian"));
        verify(eventListner, times(2)).handleErrorListener(any(ErrorEvent.class));
    }


    @Test
    @Order(11)
    void insertBookTest() {
        bookStorageService.insertBook("123", "Tolstoy", "Drama");

        assertNotNull(bookService.getByName("123"));
    }

    @Test
    @Order(12)
    void updateBookTest() {
        bookStorageService.updateBook("Martian", "NewMartian", "Tolstoy", "Drama");

        Book book = bookService.getByName("NewMartian");
        assertNotNull(book);
    }

    @Test
    @Order(13)
    void addCommentTest() {
        List<Comment> comments = bookStorageService.getCommentsForBook("Lord of the Rings");
        assertEquals(comments.size(), 0);

        bookStorageService.addComment("Lord of the Rings", "TestComment");

        comments = bookStorageService.getCommentsForBook("Lord of the Rings");
        assertEquals(comments.size(), 1);
    }

    @Test
    @Order(14)
    void deleteCommentByIdTest() {
        List<Comment> comments = bookStorageService.getCommentsForBook("Martian");
        assertEquals(comments.size(), 2);

        bookStorageService.deleteCommentById(comments.get(0).getId());

        comments = bookStorageService.getCommentsForBook("Martian");
        assertEquals(comments.size(), 1);

    }

    @Test
    @Order(15)
    void deleteBookTest() {
        Book book = bookService.getByName("Lord of the Rings");
        assertNotNull(book);

        bookStorageService.deleteBook("Lord of the Rings");

        book = bookService.getByName("Lord of the Rings");
        assertNull(book);
    }
}