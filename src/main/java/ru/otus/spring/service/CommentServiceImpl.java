package ru.otus.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.spring.dao.BookDao;
import ru.otus.spring.dao.CommentDao;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Comment;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService{

    private final CommentDao commentRepository;

    @Autowired
    public CommentServiceImpl(CommentDao commentDaookDao) {
        this.commentRepository = commentDaookDao;
    }

    @Override
    public void addComment(Comment comment) {
        commentRepository.addComment(comment);
    }

    @Override
    public List<Comment> findCommentsByBook(Book book) {
        return commentRepository.findCommentsByBook(book);
    }

    @Override
    public void deleteComment(Comment comment) {
        commentRepository.deleteComment(comment);
    }

    @Override
    public Comment findCommentByID(long id) {
       return commentRepository.findCommentByID(id);
    }
}
