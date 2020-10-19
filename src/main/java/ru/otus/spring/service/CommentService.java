package ru.otus.spring.service;

import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Comment;
import ru.otus.spring.domain.Genre;

import java.util.List;

public interface CommentService {

    void addComment(Comment comment);

    List<Comment> findCommentsByBook(Book book);

    void deleteComment(Comment comment);

    Comment findCommentByID(long id);

}
