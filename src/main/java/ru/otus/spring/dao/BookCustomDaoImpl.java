package ru.otus.spring.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;
import ru.otus.spring.domain.Book;

import static org.springframework.data.mongodb.core.query.Criteria.where;

@RequiredArgsConstructor
@Component
public class BookCustomDaoImpl implements BookCustomDao{

    private final MongoTemplate mongoTemplate;

    @Override
    public void deleteCommentById(String id) {
        Query query = new Query(where("id").is(id));
        Update update = new Update().pull("comments", query );
        mongoTemplate.updateMulti(new Query(),update, Book.class);
    }

}
