package io.github.maxixcom.otus.booklib.dao;

import io.github.maxixcom.otus.booklib.domain.Author;
import io.github.maxixcom.otus.booklib.domain.Book;
import io.github.maxixcom.otus.booklib.domain.Genre;
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

    @Test
    void shouldReturnBookById() {
        Book expectedBook = Book.builder()
                .id(1L)
                .title("Капитанская дочка")
                .author(Author.builder()
                        .id(1L)
                        .name("Александр Пушкин")
                        .build())
                .genre(Genre.builder()
                        .id(2)
                        .title("Классика")
                        .build())
                .build();

        Optional<Book> bookOptional = bookDaoJdbc.findById(1);

        Assertions.assertThat(bookOptional)
                .isPresent()
                .containsInstanceOf(Book.class)
                .hasValue(expectedBook);
    }

    @Test
    void shouldInsertBook() {
        Book book = Book.builder()
                .title("Book Title")
                .author(Author.builder()
                        .id(1L)
                        .build())
                .genre(Genre.builder()
                        .id(2)
                        .build())
                .build();

        long id = bookDaoJdbc.insert(book);

        Assertions.assertThat(id).isEqualTo(100);

        Optional<Book> bookOptional = bookDaoJdbc.findById(100);
        Assertions.assertThat(bookOptional)
                .isPresent()
                .containsInstanceOf(Book.class)
                .hasValueSatisfying(b -> {
                    Assertions.assertThat(b.getTitle()).isEqualTo("Book Title");
                    Assertions.assertThat(b.getAuthor().getId()).isEqualTo(1);
                    Assertions.assertThat(b.getGenre().getId()).isEqualTo(2);
                });
    }
}