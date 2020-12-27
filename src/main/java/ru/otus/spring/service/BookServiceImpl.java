package ru.otus.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.dao.BookCustomDao;
import ru.otus.spring.dao.BookDao;
import ru.otus.spring.domain.Book;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {
    private final BookDao bookRepository;
    private final BookCustomDao bookCustomRepository;

    @Autowired
    public BookServiceImpl(BookDao bookDao, BookCustomDao bookCustomDao) {
        this.bookRepository = bookDao;
        this.bookCustomRepository = bookCustomDao;
    }

    @Override
    public Book getById(String id) {
        return bookRepository.findById(id).orElse(null);
    }

    @Override
    public Book getByName(String name) {
        return bookRepository.findByNameIgnoreCase(name).orElse(null);
    }

    @Override
    public List<Book> getAll() {
        return bookRepository.findAll();
    }

    @Override
    @Transactional
    public void insert(Book book) {
        bookRepository.save(book);
    }

    @Override
    @Transactional
    public void update(Book book) {
        bookRepository.save(book);
    }

    @Override
    @Transactional
    public void deleteById(String id) {
        bookRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteCommentById(String id) {
        bookCustomRepository.deleteCommentById(id);
    }
}
