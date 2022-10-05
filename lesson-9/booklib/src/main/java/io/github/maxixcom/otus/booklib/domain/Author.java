package io.github.maxixcom.otus.booklib.domain;

import lombok.Builder;
import lombok.Data;
@Builder
@Data
public class Author {
    private final Long id;
    private final String name;
}
