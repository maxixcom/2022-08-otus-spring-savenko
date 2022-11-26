package io.github.maxixcom.otus.booklib.dto;

import io.github.maxixcom.otus.booklib.domain.Book;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Optional;

@Data
@AllArgsConstructor
public class BookDto {
    private final String id;
    private final String title;
    private final AuthorDto author;
    private final GenreDto genre;

    public static BookDto fromDomainObject(Book book) {
        return new BookDto(
                book.getId(),
                book.getTitle(),
                Optional.ofNullable(book.getAuthor())
                        .map(AuthorDto::fromDomainObject)
                        .orElse(null),
                Optional.ofNullable(book.getGenre())
                        .map(GenreDto::fromDomainObject)
                        .orElse(null)
        );
    }
}
