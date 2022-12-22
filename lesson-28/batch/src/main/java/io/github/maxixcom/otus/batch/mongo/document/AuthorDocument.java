package io.github.maxixcom.otus.batch.mongo.document;

import io.github.maxixcom.otus.batch.domain.Author;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@RequiredArgsConstructor
public class AuthorDocument {
    private final String id;
    private final String name;

    public static AuthorDocument fromDomainObject(Author author) {
        return new AuthorDocument(null, author.getName());
    }
}
