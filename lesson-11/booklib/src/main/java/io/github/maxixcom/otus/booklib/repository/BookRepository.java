package io.github.maxixcom.otus.booklib.repository;

import io.github.maxixcom.otus.booklib.domain.Book;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface BookRepository {
    Optional<Book> findById(long id);

    List<Book> findAll();

    void deleteByIds(Set<Long> ids);

    Book save(Book book);
}
