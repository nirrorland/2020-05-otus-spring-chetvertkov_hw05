package ru.otus.spring.domain;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "comments")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment     {

    @Id
    //@Field(name ="comment_id")
    private String id;


    @DBRef
    @Field(name = "book")
    private Book book;

    @Field(name = "text")
    private String text;

    public Comment(Book book, String text) {
        this.book = book;
        this.text = text;
    }

    @Override
    public String toString() {
        return "Comment{ID=" + id + ";" + "Text=" + text + ";}";
    }
}
