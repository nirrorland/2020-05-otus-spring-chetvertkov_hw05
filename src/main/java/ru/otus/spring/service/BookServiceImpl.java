package ru.otus.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.spring.dao.BookDao;
import ru.otus.spring.domain.Book;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {
    private final BookDao bookRepository;

    @Autowired
    public BookServiceImpl(BookDao BookDao) {
        this.bookRepository = BookDao;
    }

    @Override
    public Book getById(long id) {
        return bookRepository.getById(id);
    }

    @Override
    public Book getByName(String name) {
        return bookRepository.getByName(name);
    }

    @Override
    public List<Book> getAll() {
        return bookRepository.getAll();
    }

    @Override
    public void insert(Book book) {
        bookRepository.insert(book);
    }

    @Override
    public void update(Book book) {
        bookRepository.update(book);
    }

    @Override
    public void deleteById(long id) {
        bookRepository.deleteById(id);
    }


}
