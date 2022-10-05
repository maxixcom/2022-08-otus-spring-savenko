package io.github.maxixcom.otus.booklib.dao;

import io.github.maxixcom.otus.booklib.domain.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorDao {
    Optional<Author> findById(long id);

    List<Author> findAll();

    long insert(Author author);
}
