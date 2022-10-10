package io.github.maxixcom.otus.booklib.repository;

import io.github.maxixcom.otus.booklib.domain.Author;

import java.util.List;

public interface AuthorRepository {
    List<Author> findAll();
}
