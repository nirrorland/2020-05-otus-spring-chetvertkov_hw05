package ru.otus.spring.mongock.changelog;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.github.cloudyrock.mongock.driver.mongodb.springdata.v3.decorator.impl.MongockTemplate;
import com.mongodb.client.MongoDatabase;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Comment;
import ru.otus.spring.domain.Genre;
import java.util.ArrayList;
import java.util.List;

@ChangeLog
public class DatabaseChangelog {

    private List<Author> authors = new ArrayList<>();
    private List<Genre> genres = new ArrayList<>();
    private List<Book> books = new ArrayList<>();
    private List<Comment> comments = new ArrayList<>();

    @ChangeSet(order = "001", id = "dropDb", author = "kir", runAlways = true)
    public void dropDb(MongoDatabase db) {
        db.drop();
    }

    @ChangeSet(order = "002", id = "insertAuthors", author = "kir")
    public void insertAuthors(MongockTemplate mt) {
        authors.add(mt.save(new Author("Tolkien")));
        authors.add(mt.save(new Author("Machiavelli")));
        authors.add(mt.save(new Author("Tolstoy")));
    }

    @ChangeSet(order = "003", id = "insertGenres", author = "kir")
    public void insertGenres(MongockTemplate mt) {
        genres.add(mt.save(new Genre("Drama")));
        genres.add(mt.save(new Genre("Comics")));
        genres.add(mt.save(new Genre("History")));
    }

    @ChangeSet(order = "004", id = "insertBooks", author = "kir")
    public void insertBooks(MongockTemplate mt) {
        books.add(mt.save(new Book("Lord of the Rings", authors.get(0), genres.get(0))));
        books.add(mt.save(new Book("Istorie Florentine", authors.get(1), genres.get(2))));
        books.add(mt.save(new Book("Martian", authors.get(2), genres.get(1))));
    }

    @ChangeSet(order = "005", id = "insertComments", author = "kir")
    public void insertComments(MongockTemplate mt) {
        comments.add(mt.save(new Comment(books.get(2),"Martian Comment 1")));
        comments.add(mt.save(new Comment(books.get(2),"Martian Comment 2")));
    }
}
