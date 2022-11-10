package io.github.maxixcom.otus.booklib.dto;

import io.github.maxixcom.otus.booklib.domain.Author;
import io.github.maxixcom.otus.booklib.domain.Book;
import io.github.maxixcom.otus.booklib.domain.Genre;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Optional;

@Data
@AllArgsConstructor
public class UpdateBookDto {
    private long id;

    @NotBlank
    @Size(max = 255)
    private String title;

    private Long authorId;

    private Long genreId;

    public static UpdateBookDto fromDomainObject(Book book) {
        return new UpdateBookDto(
                book.getId(),
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
