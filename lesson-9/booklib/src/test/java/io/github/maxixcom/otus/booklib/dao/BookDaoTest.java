package io.github.maxixcom.otus.booklib.dao;

import io.github.maxixcom.otus.booklib.domain.Book;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@JdbcTest
@Import(BookDaoJdbc.class)
class BookDaoTest {
    @Autowired
    private BookDaoJdbc bookDaoJdbc;

    @Test
    void shouldReturnAllBooks() {
        List<Book> bookList = bookDaoJdbc.findAll();
        Assertions.assertThat(bookList).size().isEqualTo(10);
    }

    @Test
    void shouldDeleteSetOfBooks() {
        Assertions.assertThat(bookDaoJdbc.findById(1)).isPresent();
        Assertions.assertThat(bookDaoJdbc.findById(2)).isPresent();

        bookDaoJdbc.deleteByIds(Set.of(1L, 2L));

        Assertions.assertThat(bookDaoJdbc.findById(1)).isEmpty();
        Assertions.assertThat(bookDaoJdbc.findById(2)).isEmpty();
    }
}