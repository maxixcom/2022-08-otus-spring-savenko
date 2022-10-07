package io.github.maxixcom.otus.booklib.dao;

import io.github.maxixcom.otus.booklib.domain.Author;

import java.util.List;

public interface AuthorDao {
    List<Author> findAll();
}
