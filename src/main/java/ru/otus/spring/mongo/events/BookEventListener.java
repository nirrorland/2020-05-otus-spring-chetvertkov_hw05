package ru.otus.spring.mongo.events;

import lombok.RequiredArgsConstructor;
import org.bson.Document;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.AfterDeleteEvent;
import org.springframework.stereotype.Component;
import ru.otus.spring.dao.CommentDao;
import ru.otus.spring.domain.Book;

@Component
@RequiredArgsConstructor
public class BookEventListener extends AbstractMongoEventListener<Book> {

    private final CommentDao commentRepository;

    @Override
    public void onAfterDelete(AfterDeleteEvent<Book> event) {
        super.onAfterDelete(event);
        Document document = event.getSource();
        commentRepository.deleteAllByBookId(document.get("_id").toString());
    }
}
