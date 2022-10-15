package io.github.maxixcom.otus.booklib.service.comment;

import io.github.maxixcom.otus.booklib.domain.Book;
import io.github.maxixcom.otus.booklib.domain.BookComment;
import io.github.maxixcom.otus.booklib.exception.BookNotFoundException;
import io.github.maxixcom.otus.booklib.repository.BookCommentRepository;
import io.github.maxixcom.otus.booklib.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class BookCommentServiceImpl implements BookCommentService {
    private final BookRepository bookRepository;
    private final BookCommentRepository bookCommentRepository;

    @Transactional(readOnly = true)
    @Override
    public List<BookComment> getAllBookComments(long bookId) {
        return bookRepository.findByIdWithComments(bookId)
                .map(Book::getBookComments)
                .orElse(Collections.emptyList());
    }

    @Transactional
    @Override
    public void createBookComment(long bookId, String commentText) {
        bookRepository.findById(bookId)
                .map(book -> {
                    BookComment bookComment = new BookComment();
                    bookComment.setBook(book);
                    bookComment.setComment(commentText);

                    book.getBookComments()
                            .add(bookComment);

                    return book;
                })
                .ifPresentOrElse(
                        bookRepository::save,
                        () -> {
                            throw new BookNotFoundException("Book #" + bookId + " not found");
                        }
                );
    }

    @Transactional
    @Override
    public void updateBookComment(long commentId, String commentText) {
        bookCommentRepository.findById(commentId)
                .map(bookComment -> {
                    bookComment.setComment(commentText);
                    return bookComment;
                })
                .ifPresent(bookCommentRepository::save);
    }

    @Transactional
    @Override
    public void deleteBookComments(Set<Long> commentIds) {
        bookCommentRepository.deleteByIds(commentIds);
    }
}
