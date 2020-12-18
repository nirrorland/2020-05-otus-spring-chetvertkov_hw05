package ru.otus.spring.dao;

import org.springframework.stereotype.Repository;
import ru.otus.spring.domain.Book;
import javax.persistence.*;
import java.util.List;

@SuppressWarnings({"SqlNoDataSourceInspection", "ConstantConditions", "SqlDialectInspection"})
@Repository
public class BookDaoJpa implements BookDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Book getById(long id) {
        return em.find(Book.class, id);
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
    public void insert(Book book) {

        em.persist(book);
    }

    @Override
    public void update(Book book) {

        em.merge(book);
    }

    @Override
    public void deleteById(long id) {
        Book book = getById(id);
        if (book != null) {
            em.remove(book);
        }
    }
}
