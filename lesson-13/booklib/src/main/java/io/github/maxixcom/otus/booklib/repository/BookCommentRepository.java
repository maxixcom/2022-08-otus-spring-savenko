package io.github.maxixcom.otus.booklib.repository;

import io.github.maxixcom.otus.booklib.domain.BookComment;

import java.util.Optional;
import java.util.Set;

public interface BookCommentRepository {
    Optional<BookComment> findById(long id);

    void deleteByIds(Set<Long> ids);

    BookComment save(BookComment bookComment);
}
