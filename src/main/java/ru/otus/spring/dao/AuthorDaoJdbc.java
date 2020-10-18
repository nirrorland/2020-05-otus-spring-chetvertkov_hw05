package ru.otus.spring.dao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.otus.spring.domain.Author;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class AuthorDaoJdbc implements AuthorDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Author getById(long id) {
        TypedQuery<Author> query = em.createQuery(
                "SELECT a FROM Author a WHERE a.id = :id"
                , Author.class);
        query.setParameter("id", id);
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public List<Author> getAll() {
        return em.createQuery("select a from Author a", Author.class).getResultList();
    }

    @Override
    public Author getByName(String name) {
        TypedQuery<Author> query = em.createQuery(
                "SELECT a FROM Author a where lower(a.name) = lower(:name)"
                , Author.class);
        query.setParameter("name", name);
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
