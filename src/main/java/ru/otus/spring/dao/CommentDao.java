package ru.otus.spring.dao;

import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Comment;

import java.util.List;

public interface CommentDao {

    void addComment(Comment comment);

    List<Comment> findCommentsByBook(Book book);

    void deleteComment(Comment comment);
}
