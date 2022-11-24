package io.github.maxixcom.otus.booklib.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Document(collection = "author")
public class Author {
    @Id
    private String id;

    @Field(name = "name")
    private String name;
}
