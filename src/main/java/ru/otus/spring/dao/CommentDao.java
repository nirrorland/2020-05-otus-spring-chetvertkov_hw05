package ru.otus.spring.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentDao extends JpaRepository<Comment, Long> {

    Optional<Comment> findById(long id);

    Comment save(Comment comment);

    //List<Comment> findCommentsByBook(Book book);

    void delete(Comment comment);

    Comment findCommentByID(long id);
}
