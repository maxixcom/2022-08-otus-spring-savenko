package io.github.maxixcom.otus.booklib.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
public class CreateBookDto {
    @NotBlank
    @Size(max = 255)
    private String title;

    private Long authorId;

    private Long genreId;
}
