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
@Document(collection = "comment")
public class Comment {
    @Id
    private ObjectId id;

    @Field("bookId")
    private ObjectId bookId;

    @Field("comment")
    private String comment;
}
