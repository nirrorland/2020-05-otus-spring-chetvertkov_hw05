//package ru.otus.spring.dao;
//
//import org.springframework.stereotype.Repository;
//import ru.otus.spring.domain.Comment;
//import javax.persistence.EntityManager;
//import javax.persistence.PersistenceContext;
//
//@Repository
//public class CommentDaoJpa implements CommentDao {
//
//    @PersistenceContext
//    private EntityManager em;
//
//    @Override
//    public void addComment(Comment comment) {
//        em.persist(comment);
//        em.flush();
//        em.clear();
//    }
//
//    @Override
//    public void deleteComment(Comment comment) {
//        em.remove(em.contains(comment) ? comment : em.merge(comment));
//        em.remove(comment);
//        em.flush();
//        em.clear();
//    }
//
//    @Override
//    public Comment findCommentByID(long id) {
//        return em.find(Comment.class, id);
//    }
//}
