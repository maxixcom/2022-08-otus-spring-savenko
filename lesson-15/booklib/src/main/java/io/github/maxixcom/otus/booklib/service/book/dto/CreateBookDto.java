package io.github.maxixcom.otus.booklib.service.book.dto;

import lombok.Data;

@Data
public class CreateBookDto {
    private final String title;
    private final Long authorId;
    private final Long genreId;
}
