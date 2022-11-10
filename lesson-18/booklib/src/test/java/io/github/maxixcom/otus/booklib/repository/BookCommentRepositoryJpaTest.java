package io.github.maxixcom.otus.booklib.repository;

import io.github.maxixcom.otus.booklib.domain.Book;
import io.github.maxixcom.otus.booklib.domain.BookComment;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;
import java.util.Set;

@DataJpaTest
class BookCommentRepositoryJpaTest {
    @Autowired
    private TestEntityManager em;

    @Autowired
    private BookCommentRepository bookCommentRepository;

    @Test
    void shouldReturnBookCommentById() {
        Book book = em.find(Book.class, 1L);

        BookComment bookComment = new BookComment();
        bookComment.setComment("Book comment");
        bookComment.setBook(book);

        em.persistAndFlush(bookComment);
        em.detach(bookComment);

        Assertions.assertThat(bookComment.getId()).isGreaterThan(0);

        Optional<BookComment> bookCommentOptional = bookCommentRepository.findById(bookComment.getId());

        Assertions.assertThat(bookCommentOptional)
                .isPresent()
                .containsInstanceOf(BookComment.class)
                .hasValueSatisfying(bc ->
                        Assertions.assertThat(bc)
                                .usingRecursiveComparison()
                                .isEqualTo(bookComment)
                );
    }


    @Test
    void shouldDeleteSetOfBookComments() {
        Book book = em.find(Book.class, 1L);

        BookComment bookComment_1 = new BookComment();
        bookComment_1.setComment("Book comment 1");
        bookComment_1.setBook(book);
        em.persist(bookComment_1);

        BookComment bookComment_2 = new BookComment();
        bookComment_2.setComment("Book comment 2");
        bookComment_2.setBook(book);
        em.persist(bookComment_2);

        em.flush();

        Assertions.assertThat(bookComment_1.getId()).isGreaterThan(0);
        Assertions.assertThat(bookComment_2.getId()).isGreaterThan(0);

        em.detach(bookComment_1);
        em.detach(bookComment_2);

        bookCommentRepository.deleteAllByIdInBatch(Set.of(
                bookComment_1.getId(),
                bookComment_2.getId()
        ));

        Assertions.assertThat(em.find(BookComment.class, bookComment_1.getId())).isNull();
        Assertions.assertThat(em.find(BookComment.class, bookComment_2.getId())).isNull();
    }
}