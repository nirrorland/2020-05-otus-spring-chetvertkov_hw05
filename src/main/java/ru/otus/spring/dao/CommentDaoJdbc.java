package ru.otus.spring.dao;

import org.springframework.stereotype.Repository;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Comment;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class CommentDaoJdbc implements CommentDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public void addComment(Comment comment) {
        em.persist(comment);
    }

    @Override
    public List<Comment> findCommentsByBook(Book book) {
        TypedQuery<Comment> query = em.createQuery("SELECT c FROM Comment c WHERE c.book =:book", Comment.class).setParameter("book", book);
        return query.getResultList();
    }

    @Override
    @Transactional
    public void deleteComment(Comment comment) {
        em.remove(comment);
    }
}
