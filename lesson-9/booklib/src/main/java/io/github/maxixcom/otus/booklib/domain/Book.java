package io.github.maxixcom.otus.booklib.domain;


import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Book {
    private final long id;
    private final String title;
    private final Author author;
    private final Genre genre;
}
