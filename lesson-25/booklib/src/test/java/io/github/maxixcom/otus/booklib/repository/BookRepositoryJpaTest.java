package io.github.maxixcom.otus.booklib.repository;

import io.github.maxixcom.otus.booklib.domain.Author;
import io.github.maxixcom.otus.booklib.domain.Book;
import io.github.maxixcom.otus.booklib.domain.Genre;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@DataJpaTest
class BookRepositoryJpaTest {
    @Autowired
    private TestEntityManager em;
    @Autowired
    private BookRepository bookRepository;

    @Test
    void shouldReturnBookById() {
        Book expectedBook = em.find(Book.class, 1L);
        Optional<Book> bookOptional = bookRepository.findById(1L);

        Assertions.assertThat(bookOptional)
                .isPresent()
                .containsInstanceOf(Book.class)
                .hasValueSatisfying(b -> {
                    Assertions.assertThat(b).usingRecursiveComparison().isEqualTo(expectedBook);
                });
    }

    @Test
    void shouldReturnAllBooks() {
        List<Book> bookList = bookRepository.findAll();

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

        Book savedBook = bookRepository.save(book);

        Assertions.assertThat(savedBook.getId()).isGreaterThan(0);

        em.find(Book.class, savedBook.getId());

        Book actualBook = em.find(Book.class, savedBook.getId());
        org.junit.jupiter.api.Assertions.assertAll(
                () -> Assertions.assertThat(actualBook).isNotNull(),
                () -> Assertions.assertThat(actualBook.getAuthor().getId()).isEqualTo(1),
                () -> Assertions.assertThat(actualBook.getGenre().getId()).isEqualTo(2)
        );
    }

    @Test
    void shouldUpdateBook() {
        Book book = em.find(Book.class, 1L);
        book.setTitle("Book Title");

        bookRepository.save(book);

        em.flush();
        em.detach(book);


        Book actualBook = em.find(Book.class, 1L);
        org.junit.jupiter.api.Assertions.assertAll(
                () -> Assertions.assertThat(actualBook).isNotNull(),
                () -> Assertions.assertThat(actualBook.getTitle()).isEqualTo("Book Title")
        );
    }

    @Test
    void shouldDeleteSetOfBooks() {
        Assertions.assertThat(em.find(Book.class, 1L)).isNotNull();
        Assertions.assertThat(em.find(Book.class, 2L)).isNotNull();

        bookRepository.deleteAllByIdInBatch(Set.of(1L, 2L));
        em.clear();

        Assertions.assertThat(em.find(Book.class, 1L)).isNull();
        Assertions.assertThat(em.find(Book.class, 2L)).isNull();
    }
}