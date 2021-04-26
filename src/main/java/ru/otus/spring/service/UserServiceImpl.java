package ru.otus.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import ru.otus.spring.dao.AuthorDao;
import ru.otus.spring.dao.UserDao;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.User;

import java.util.List;

@Service
public class UserServiceImpl implements UserDetailsService {
    private final UserDao userRepository;

    @Autowired
    public UserServiceImpl(UserDao userDao) {
        this.userRepository = userDao;
    }


    @Override
    public UserDetails loadUserByUsername(String username) {
        return userRepository.findByUsernameIgnoreCase(username).orElse(null);
    }
}
