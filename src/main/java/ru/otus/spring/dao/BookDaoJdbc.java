package ru.otus.spring.dao;

import org.springframework.stereotype.Repository;
import ru.otus.spring.domain.Book;
import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.List;

@SuppressWarnings({"SqlNoDataSourceInspection", "ConstantConditions", "SqlDialectInspection"})
@Repository
public class BookDaoJdbc implements BookDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Book getById(long id) {
        TypedQuery<Book> query = em.createQuery(
                "SELECT b FROM Book b WHERE b.id = :id"
                , Book.class);
        query.setParameter("id", id);
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public List<Book> getAll() {
        return em.createQuery("SELECT b FROM Book b", Book.class).getResultList();
    }

    @Override
    public Book getByName(String name) {
        TypedQuery<Book> query = em.createQuery(
                "SELECT b FROM Book b where lower(b.name) = lower(:name)"
                , Book.class);
        query.setParameter("name", name);
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    @Transactional
    public void insert(Book book) {


        em.persist(book);
    }

    @Override
    @Transactional
    public void update(Book book) {
        Query query = em.createQuery("UPDATE Book b " +
                "  SET " +
                "      b.name = :BOOK_NAME," +
                "      b.author = :AUTHOR_ID," +
                "      b.genre = :GENRE_ID" +
                "   WHERE b.id = :BOOK_ID");

        query.setParameter("BOOK_ID", book.getId());
        query.setParameter("BOOK_NAME", book.getName());
        query.setParameter("AUTHOR_ID", book.getAuthor());
        query.setParameter("GENRE_ID", book.getGenre());
        query.executeUpdate();
       // em.merge(book);
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        Query query = em.createQuery("DELETE Book b " +
                "WHERE b.id = :BOOK_ID");
        query.setParameter("BOOK_ID", id);
        query.executeUpdate();
    }
}
