package io.github.maxixcom.otus.booklib.repository;

import io.github.maxixcom.otus.booklib.domain.Author;
import io.github.maxixcom.otus.booklib.domain.Book;
import io.github.maxixcom.otus.booklib.domain.Genre;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@DataJpaTest
@Import(BookRepositoryJpa.class)
class BookRepositoryJpaTest {
    @Autowired
    private TestEntityManager em;
    @Autowired
    private BookRepositoryJpa bookRepositoryJpa;

    @Test
    void shouldReturnBookById() {
        Book expectedBook = em.find(Book.class, 1L);
        Optional<Book> bookOptional = bookRepositoryJpa.findById(1L);

        Assertions.assertThat(bookOptional)
                .isPresent()
                .containsInstanceOf(Book.class)
                .hasValueSatisfying(b -> {
                    Assertions.assertThat(b).usingRecursiveComparison().isEqualTo(expectedBook);
                });
    }

    @Test
    void shouldReturnAllBooks() {
        List<Book> bookList = bookRepositoryJpa.findAll();

        Assertions.assertThat(bookList).hasSizeGreaterThan(0);
    }

    @Test
    void shouldInsertBook() {
        Book book = new Book(
                0,
                "Book Title",
                new Author(1L, null),
                new Genre(2L, null),
                Collections.emptyList()
        );

        Book savedBook = bookRepositoryJpa.save(book);

        Assertions.assertThat(savedBook.getId()).isGreaterThan(0);

        Optional<Book> bookOptional = bookRepositoryJpa.findById(savedBook.getId());
        Assertions.assertThat(bookOptional)
                .isPresent()
                .containsInstanceOf(Book.class)
                .hasValueSatisfying(b -> {
                    Assertions.assertThat(b.getTitle()).isEqualTo("Book Title");
                    Assertions.assertThat(b.getAuthor().getId()).isEqualTo(1);
                    Assertions.assertThat(b.getGenre().getId()).isEqualTo(2);
                });
    }

    @Test
    void shouldUpdateBook() {
        Book book = em.find(Book.class, 1L);
        book.setTitle("Book Title");

        bookRepositoryJpa.save(book);

        em.flush();
        em.detach(book);

        Optional<Book> bookOptional = bookRepositoryJpa.findById(1);

        Assertions.assertThat(bookOptional)
                .isPresent()
                .containsInstanceOf(Book.class)
                .hasValueSatisfying(b -> {
                    Assertions.assertThat(b.getTitle()).isEqualTo("Book Title");
                });
    }

    @Test
    void shouldDeleteSetOfBooks() {
        Assertions.assertThat(bookRepositoryJpa.findById(1)).isPresent();
        Assertions.assertThat(bookRepositoryJpa.findById(2)).isPresent();

        bookRepositoryJpa.deleteByIds(Set.of(1L, 2L));
        em.clear();

        Assertions.assertThat(bookRepositoryJpa.findById(1)).isEmpty();
        Assertions.assertThat(bookRepositoryJpa.findById(2)).isEmpty();
    }
}