package io.github.maxixcom.otus.booklib.dto;

import io.github.maxixcom.otus.booklib.domain.Author;
import io.github.maxixcom.otus.booklib.domain.Book;
import io.github.maxixcom.otus.booklib.domain.Genre;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Optional;

@Data
@AllArgsConstructor
public class UpdateBookDto {
    private String title;

    private String authorId;

    private String genreId;

    public static UpdateBookDto fromDomainObject(Book book) {
        return new UpdateBookDto(
                book.getTitle(),
                Optional.ofNullable(book.getAuthor())
                        .map(Author::getId)
                        .orElse(null),
                Optional.ofNullable(book.getGenre())
                        .map(Genre::getId)
                        .orElse(null)
        );
    }
}
