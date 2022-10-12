package io.github.maxixcom.otus.booklib.service.selector;

import io.github.maxixcom.otus.booklib.domain.Genre;

import java.util.Optional;

public interface GenreSelectorService {
    Optional<Long> selectGenre();
}
