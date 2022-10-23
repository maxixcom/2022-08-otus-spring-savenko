package io.github.maxixcom.otus.booklib.service.book;

import io.github.maxixcom.otus.booklib.domain.Book;
import io.github.maxixcom.otus.booklib.domain.Comment;
import io.github.maxixcom.otus.booklib.repository.BookRepository;
import io.github.maxixcom.otus.booklib.repository.CommentRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;
import java.util.Set;

@DirtiesContext
@SpringBootTest
class BookServiceImplTest {
    @Autowired
    BookService bookService;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    CommentRepository commentRepository;

    @Test
    void deleteBookShouldDeleteItsComments() {
        Book book = new Book();
        book = bookRepository.save(book);

        Comment comment = new Comment();
        comment.setBookId(book.getId());
        comment.setComment("Comment");
        commentRepository.save(comment);

        bookService.deleteBooks(Set.of(book.getId().toString()));

        List<Comment> comments = commentRepository.findAllByBookId(book.getId());

        Assertions.assertThat(comments).isEmpty();
    }
}