package io.github.maxixcom.otus.booklib.dao;

import io.github.maxixcom.otus.booklib.domain.Book;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;

import java.util.List;

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
}