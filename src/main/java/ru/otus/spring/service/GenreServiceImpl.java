package ru.otus.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.spring.dao.GenreDao;
import ru.otus.spring.domain.Genre;

import java.util.List;

@Service
public class GenreServiceImpl implements GenreService {
    private final GenreDao genreRepository;

    @Autowired
    public GenreServiceImpl(GenreDao genreDao) {
        this.genreRepository = genreDao;
    }

    @Override
    public Genre getById(int id) {
        return genreRepository.getById(id);
    }

    @Override
    public List<Genre> getAll() {
        return genreRepository.getAll();
    }

    @Override
    public Genre getByName(String name) {
        return genreRepository.getByName(name);
    }
}
