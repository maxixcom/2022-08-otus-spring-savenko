package io.github.maxixcom.otus.booklib.dao;

import io.github.maxixcom.otus.booklib.domain.Book;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface BookDao {
    Optional<Book> findById(long id);

    List<Book> findAll();

    void deleteByIds(Set<Long> ids);

    void update(Book book);
}
