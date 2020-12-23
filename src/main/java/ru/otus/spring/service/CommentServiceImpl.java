package ru.otus.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.dao.CommentDao;
import ru.otus.spring.domain.Comment;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService{

    private final CommentDao commentRepository;

    @Autowired
    public CommentServiceImpl(CommentDao commentDaookDao) {
        this.commentRepository = commentDaookDao;
    }

    @Override
    @Transactional
    public void addComment(Comment comment) {
        commentRepository.save(comment);
    }

    @Override
    @Transactional
    public void deleteComment(Comment comment) {
        commentRepository.deleteById(comment.getId());
    }

    @Override
    public void deleteById(String id) {
        commentRepository.deleteById(id);

    }

    @Override
    public List<Comment> findCommentsByBookId(String bookId) {
        return commentRepository.findAllByBookId(bookId);
    }

    @Override
    public Comment findCommentByID(String id) {
       return commentRepository.findById(id).orElse(null);
    }
}
