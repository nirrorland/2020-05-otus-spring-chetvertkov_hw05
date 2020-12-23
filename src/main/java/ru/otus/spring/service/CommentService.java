package ru.otus.spring.service;

import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Comment;

import java.util.List;

public interface CommentService {

    void addComment(Comment comment);

    void deleteComment(Comment comment);

    void deleteById(String id);

    Comment findCommentByID(String id);

    List<Comment> findCommentsByBookId(String bookId);

}
