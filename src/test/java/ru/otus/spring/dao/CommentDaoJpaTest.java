package ru.otus.spring.dao;

import org.junit.Assert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Comment;
import java.util.List;

@DataJpaTest
public class CommentDaoJpaTest {
    @Autowired
    private CommentDao commentRepo;

    @Autowired
    private BookDao bookRepo;

    @Test
    @DisplayName("addComment добавляет комментарий")
    void addComment() {
        Book book = bookRepo.findByName("Martian").get();
        List<Comment> comments = commentRepo.findByBook(book);
        int size = comments.size();

        Comment comment = new Comment(book, "test comment");

        commentRepo.saveAndFlush(comment);
        comments = commentRepo.findByBook(book);
        Assert.assertEquals(comments.size(), size + 1);
    }

    @Test
    @DisplayName("deleteComment удаляет комментарий")
    void deleteComment() {
        Book book = bookRepo.findByName("Martian").get();
        List<Comment> comments = commentRepo.findByBook(book);
        int size = comments.size();

        Comment comment = commentRepo.findById(1).get();

        commentRepo.delete(comment);

        comments = commentRepo.findByBook(book);
        Assert.assertEquals(comments.size(), size - 1);
    }

    @Test
    void findCommentForBook() {
        Book book = bookRepo.findByName("Martian").get();
        List<Comment> comments = commentRepo.findByBook(book);
        Assert.assertEquals(comments.size(), 2);
    }

    @Test
    void findCommentForBookNoResult() {
        Book book = bookRepo.findByName("Lord of the Rings").get();
        List<Comment> comments = commentRepo.findByBook(book);
        Assert.assertTrue(comments.isEmpty());
    }

    @Test
    void findCommentById() {

        Comment comment = commentRepo.findById(2).get();
        Assert.assertEquals(comment.getText(), "Martian Comment 2");
    }
}
