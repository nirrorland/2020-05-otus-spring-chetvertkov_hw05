package ru.otus.spring.service;

import org.springframework.stereotype.Service;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Comment;
import ru.otus.spring.domain.Genre;

import java.util.List;

@Service
public class BookStorageImpl implements BookStorage {
    private final AuthorService authorService;
    private final GenreService genreService;
    private final BookService bookService;
    private final CommentService commentService;
    private final ConsoleIOService consoleIOService;

    public BookStorageImpl(AuthorService authorService, GenreService genreService, BookService bookService, CommentService commentService, ConsoleIOService consoleIOService) {
        this.authorService = authorService;
        this.genreService = genreService;
        this.bookService = bookService;
        this.commentService = commentService;
        this.consoleIOService = consoleIOService;
    }

    @Override
    public List<Author> getAllAuthors() {
        return authorService.getAll();
    }

    @Override
    public List<Genre> getAllGenres() {
        return genreService.getAll();
    }

    @Override
    public List<Book> getAllBooks() {
        return bookService.getAll();
    }

    @Override
    public void insertBook(String bookName, String authorName, String genreName) {
        Author author = authorService.getByName(authorName);
        Genre genre = genreService.getByName(genreName);
        Book oldBook = bookService.getByName(bookName);


        boolean isNotReadyForInsertion = false;

        if (author == null) {
            isNotReadyForInsertion = true;
            consoleIOService.out("Author not found. Please make sure author is presented in DB.");
        }

        if (genre == null) {
            isNotReadyForInsertion = true;
            consoleIOService.out("Genre not found. Please make sure genre is presented in DB.");
        }

        if (oldBook != null) {
            isNotReadyForInsertion = true;
            consoleIOService.out("Book with same name already exists. Please enter unique name.");
        }

        if (!isNotReadyForInsertion) {
            Book book = new Book(bookName, author, genre);
            bookService.insert(book);
        }
    }

    @Override
    public void updateBook(String oldBookName, String newBookName, String newAuthorName, String newGenreName) {
        Author author = authorService.getByName(newAuthorName);
        Genre genre = genreService.getByName(newGenreName);
        Book oldBook = bookService.getByName(oldBookName);


        boolean isNotReadyForUpdate = false;

        if (author == null) {
            isNotReadyForUpdate = true;
            consoleIOService.out("Author not found. Please make sure author is presented in DB.");
        }

        if (genre == null) {
            isNotReadyForUpdate = true;
            consoleIOService.out("Genre not found. Please make sure genre is presented in DB.");
        }

        if (oldBook == null) {
            isNotReadyForUpdate = true;
            consoleIOService.out("Book with same name was not found. Please enter existed name.");
        }

        if (!isNotReadyForUpdate) {
            Book book = new Book(newBookName, author, genre);
            book.setId(oldBook.getId());
            bookService.update(book);
        }
    }

    @Override
    public void deleteBook(String bookName) {
        Book book = bookService.getByName(bookName);


        boolean isNotReadyForDelete = false;

        if (book == null) {
            isNotReadyForDelete = true;
            consoleIOService.out("Book not found. Cannot delete.");
        }

        if (!isNotReadyForDelete) {
            bookService.deleteById(book.getId());
        }
    }

    @Override
    public void addComment(String bookName, String text) {
        boolean isNotReadyForCommentInsertion = false;
        Book book = bookService.getByName(bookName);

        if (book == null) {
            isNotReadyForCommentInsertion = true;
            consoleIOService.out("Book with this name not found. Please enter existing book name.");
        }

        if (!isNotReadyForCommentInsertion) {
            Comment comment = new Comment(book, text);
            commentService.addComment(comment);
        }
    }

    @Override
    public List<Comment> getCommentsForBook(String bookName) {
        boolean isNotReady = false;
        Book book = bookService.getByName(bookName);

        if (book == null) {
            isNotReady = true;
            consoleIOService.out("Book with this name not found. Please enter existing book name.");
        }

        if (!isNotReady) {
            return commentService.findCommentsByBook(book);
        } else {
            return null;
        }
    }

    @Override
    public void deleteCommentById(long id) {

    }

}
