package io.github.maxixcom.otus.booklib.dao;

import io.github.maxixcom.otus.booklib.domain.Genre;

import java.util.List;

public interface GenreDao {
    List<Genre> findAll();
}
