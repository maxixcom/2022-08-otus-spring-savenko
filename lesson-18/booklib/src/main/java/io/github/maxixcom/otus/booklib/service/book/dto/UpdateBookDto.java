package io.github.maxixcom.otus.booklib.service.book.dto;

import lombok.Data;

@Data
public class UpdateBookDto {
    private long bookId;
    private String title;
    private Long authorId;
    private Long genreId;
}
