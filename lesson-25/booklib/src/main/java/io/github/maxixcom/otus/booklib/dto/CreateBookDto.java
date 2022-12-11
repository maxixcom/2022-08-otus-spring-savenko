package io.github.maxixcom.otus.booklib.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class CreateBookDto {
    @NotBlank
    @Size(max = 255)
    private String title;

    private Long authorId;

    private Long genreId;
}
