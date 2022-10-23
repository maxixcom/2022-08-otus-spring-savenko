package io.github.maxixcom.otus.booklib.service.book.dto;

import io.github.maxixcom.otus.booklib.domain.Author;
import io.github.maxixcom.otus.booklib.domain.Genre;
import lombok.Data;

@Data
public class CreateBookDto {
    private final String title;
    private final Author author;
    private final Genre genre;
}
