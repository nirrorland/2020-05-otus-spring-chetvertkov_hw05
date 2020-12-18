package ru.otus.spring.dao;

import org.junit.Assert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Genre;
import javax.transaction.Transactional;
import java.util.List;

@DataJpaTest
@Import({BookDaoJpa.class, AuthorDaoJpa.class, GenreDaoJpa.class})
public class BookDaoJpaTest {

    @Autowired
    private BookDaoJpa bookDao;

    @Autowired
    private AuthorDaoJpa authorDao;

    @Autowired
    private GenreDaoJpa genreDao;

    @Test
    @DisplayName("getById получает нужный экземпляр по id")
    @Transactional
    void getByIdTest() {
        Assert.assertEquals(bookDao.getById(1).getName(), "Lord of the Rings");
        Assert.assertEquals(bookDao.getById(1).getId(), 1);
        Assert.assertEquals(bookDao.getById(1).getAuthor().getName(), "Tolkien");
        Assert.assertEquals(bookDao.getById(1).getGenre().getName(), "Drama");
    }

    @Test
    @DisplayName("getById = null, когда ничего не найдено")
    @Transactional
    void getByIdNotFoundTest() {
        Assert.assertNull(bookDao.getById(0));
    }

    @Test
    @DisplayName("getAll получает все записи")
    @Transactional
    void getAllTest() {
        List<Book> result = bookDao.getAll();

        Assert.assertEquals(result.size(), 3);
        Assert.assertEquals(result.get(0).getName(), "Lord of the Rings");
        Assert.assertEquals(result.get(1).getName(), "Istorie Florentine");
        Assert.assertEquals(result.get(2).getName(), "Martian");
    }

    @Test
    @DisplayName("getByName получает нужный экземпляр по name")
    @Transactional
    void geByNameTest() {
        Book result = bookDao.getByName("Martian");

        Assert.assertEquals(result.getName(), "Martian");
        Assert.assertEquals(result.getId(), 3);
        Assert.assertEquals(result.getAuthor().getName(), "Tolstoy");
        Assert.assertEquals(result.getGenre().getName(), "Comics");
    }

    @Test
    @DisplayName("getByName не зависит от регистра")
    @Transactional
    void geByNameIgnoreCaseTest() {
        Book result = bookDao.getByName("MaRtian");

        Assert.assertEquals(result.getName(), "Martian");
        Assert.assertEquals(result.getId(), 3);
        Assert.assertEquals(result.getAuthor().getName(), "Tolstoy");
        Assert.assertEquals(result.getGenre().getName(), "Comics");
    }

    @Test
    @DisplayName("getByName = null, когда записи не найдены")
    @Transactional
    void geByNameNotFoundTest() {
        Book result = bookDao.getByName("MaRtian123");

        Assert.assertNull(result);
    }

    @Test
    @DisplayName("insert")
    @Transactional
    void insertTest() {
        List<Book> result = bookDao.getAll();
        int size = result.size();

        Author author = authorDao.getByName("Tolstoy");
        Genre genre = genreDao.getByName("Comics");

        Book newBook = new Book("name of book", author, genre);
        bookDao.insert(newBook);

        Assert.assertEquals(size + 1, bookDao.getAll().size());
        Assert.assertEquals("name of book", bookDao.getById(4).getName());
    }

    @Test
    @DisplayName("update")
    @Transactional
    void updateTest() {
        List<Book> result = bookDao.getAll();
        int size = result.size();

        Author author = authorDao.getByName("Tolstoy");
        Genre genre = genreDao.getByName("Comics");

        Book newBook = new Book(1, "name of book", author, genre);
        bookDao.update(newBook);

        Assert.assertEquals(size, bookDao.getAll().size());
        Assert.assertEquals("name of book", bookDao.getById(1).getName());
    }

    @Test
    @DisplayName("delete")
    @Transactional
    void deleteByIdTest() {
        List<Book> result = bookDao.getAll();
        int size = result.size();

        bookDao.deleteById(1);

        Assert.assertEquals(size - 1, bookDao.getAll().size());
        Assert.assertNull(bookDao.getById(1));
    }

}
