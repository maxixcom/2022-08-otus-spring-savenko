package io.github.maxixcom.otus.booklib.service.selector;

import io.github.maxixcom.otus.booklib.domain.Author;

import java.util.Optional;

public interface AuthorSelectorService {
    Optional<Long> selectAuthor();
}
