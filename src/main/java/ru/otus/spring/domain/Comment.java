package ru.otus.spring.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment {

    private String id;

    private String text;

    public Comment(String text) {
        this.id = UUID.randomUUID().toString();
        this.text = text;
    }

    @Override
    public String toString() {
        return "Comment{ID=" + id + ";" + "Text=" + text + ";}";
    }
}
