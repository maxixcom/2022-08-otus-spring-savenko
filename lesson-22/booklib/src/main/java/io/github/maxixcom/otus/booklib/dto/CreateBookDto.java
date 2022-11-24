package io.github.maxixcom.otus.booklib.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateBookDto {
    private String title;

    private String authorId;

    private String genreId;
}
