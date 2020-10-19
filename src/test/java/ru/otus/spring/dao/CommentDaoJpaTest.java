package ru.otus.spring.dao;


import org.junit.Assert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Comment;

import java.util.List;

@DataJpaTest
@Import({CommentDaoJpa.class, BookDaoJpa.class})
public class CommentDaoJpaTest {
    @Autowired
    private CommentDaoJpa commentRepo;

    @Autowired
    private BookDaoJpa bookRepo;

    @Test
    @DisplayName("addComment добавляет комментарий")
    void addComment() {
        Book book = bookRepo.getByName("Martian");
        List<Comment> comments = commentRepo.findCommentsByBook(book);
       int size = comments.size();

        Comment comment = new Comment( book, "test comment");

        commentRepo.addComment(comment);
        comments = commentRepo.findCommentsByBook(book);
        Assert.assertEquals(comments.size(), size+1);
    }

    @Test
    @DisplayName("deleteComment удаляет комментарий")
    void deleteComment() {
        Book book = bookRepo.getByName("Martian");
        List<Comment> comments = commentRepo.findCommentsByBook(book);
        int size = comments.size();

        Comment comment = commentRepo.findCommentByID(1);

        commentRepo.deleteComment(comment);

        comments = commentRepo.findCommentsByBook(book);
        Assert.assertEquals(comments.size(), size-1);
    }

    @Test
    void findCommentForBook() {
        Book book = bookRepo.getByName("Martian");
        List<Comment> comments = commentRepo.findCommentsByBook(book);
        Assert.assertEquals(comments.size(), 2);
    }

    @Test
    void findCommentForBookNoResult() {
        Book book = bookRepo.getByName("Lord of the Rings");
        List<Comment> comments = commentRepo.findCommentsByBook(book);
        Assert.assertTrue(comments.isEmpty());
    }

    @Test
    void findCommentById() {

        Comment comment = commentRepo.findCommentByID(2);
        Assert.assertEquals(comment.getText(), "Martian Comment 2");
    }


}
