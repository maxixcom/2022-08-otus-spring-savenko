package io.github.maxixcom.otus.batch.mongo.document;

import io.github.maxixcom.otus.batch.domain.Book;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Optional;

@Data
@AllArgsConstructor
public class BookDocument {
    private final String id;
    private final String title;
    private final AuthorDocument author;
    private final GenreDocument genre;

    public static BookDocument fromDomainObject(Book book) {
        return new BookDocument(
                null,
                book.getTitle(),
                Optional.ofNullable(book.getAuthor())
                        .map(AuthorDocument::fromDomainObject)
                        .orElse(null),
                Optional.ofNullable(book.getGenre())
                        .map(GenreDocument::fromDomainObject)
                        .orElse(null)
        );
    }
}
