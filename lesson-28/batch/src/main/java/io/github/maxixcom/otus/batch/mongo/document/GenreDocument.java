package io.github.maxixcom.otus.batch.mongo.document;

import io.github.maxixcom.otus.batch.domain.Genre;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.Id;

@Data
@RequiredArgsConstructor
public class GenreDocument {
    @Id
    private final String id;
    private final String title;

    public static GenreDocument fromDomainObject(Genre genre) {
        return new GenreDocument(null, genre.getTitle());
    }
}
