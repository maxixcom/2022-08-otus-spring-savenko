package io.github.maxixcom.otus.booklib.dao;

import io.github.maxixcom.otus.booklib.domain.Book;

import java.util.List;

public interface BookDao {
    List<Book> findAll();
}
