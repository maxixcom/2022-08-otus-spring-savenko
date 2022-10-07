package io.github.maxixcom.otus.booklib.domain;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Genre {
    private final long id;
    private final String title;
}
