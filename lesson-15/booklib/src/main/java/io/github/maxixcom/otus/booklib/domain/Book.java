package io.github.maxixcom.otus.booklib.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Document(collection = "books")
public class Book {
    @Id
    private ObjectId id;

    @Field("title")
    private String title;

    @Field("author")
    private Author author;

    @Field("genre")
    private Genre genre;
}
