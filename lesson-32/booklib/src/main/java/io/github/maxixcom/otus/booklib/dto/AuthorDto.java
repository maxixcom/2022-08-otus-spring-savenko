package io.github.maxixcom.otus.booklib.dto;

import io.github.maxixcom.otus.booklib.domain.Author;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class AuthorDto {
    private final long id;
    private final String name;

    public static AuthorDto fromDomainObject(Author author) {
        return new AuthorDto(author.getId(), author.getName());
    }
}
