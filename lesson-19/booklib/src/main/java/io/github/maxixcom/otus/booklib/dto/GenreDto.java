package io.github.maxixcom.otus.booklib.dto;

import io.github.maxixcom.otus.booklib.domain.Genre;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class GenreDto {
    private final long id;
    private final String title;

    public static GenreDto fromDomainObject(Genre genre) {
        return new GenreDto(genre.getId(), genre.getTitle());
    }
}
