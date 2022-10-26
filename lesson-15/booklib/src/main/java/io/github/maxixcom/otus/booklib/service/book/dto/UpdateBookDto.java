package io.github.maxixcom.otus.booklib.service.book.dto;

import io.github.maxixcom.otus.booklib.domain.Author;
import io.github.maxixcom.otus.booklib.domain.Genre;
import lombok.Data;

@Data
public class UpdateBookDto {
    private String bookId;
    private String title;
    private Author author;
    private Genre genre;
}
