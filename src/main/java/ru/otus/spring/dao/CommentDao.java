package ru.otus.spring.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentDao extends JpaRepository<Comment, Long> {

    Comment save(Comment comment);

    List<Comment> findByBook(Book book);

    void delete(Comment comment);

    Optional<Comment> findById(long id);
}
