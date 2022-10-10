package io.github.maxixcom.otus.booklib.service.comment;

import io.github.maxixcom.otus.booklib.domain.Book;
import io.github.maxixcom.otus.booklib.domain.BookComment;
import io.github.maxixcom.otus.booklib.repository.BookRepository;
import io.github.maxixcom.otus.booklib.service.io.IOService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class BookCommentCreateServiceImpl implements BookCommentCreateService {
    private final IOService ioService;
    private final BookRepository bookRepository;

    @Transactional
    @Override
    public void createComment(long bookId) {
        bookRepository.findById(bookId)
                .ifPresentOrElse(
                        this::createCommentInteractive,
                        () -> ioService.out("Book #%d not found%n", bookId)
                );
    }

    private void createCommentInteractive(Book book) {
        String commentText = ioService.readLineWithPrompt("Enter you comment: ");

        BookComment bookComment = new BookComment();
        bookComment.setBook(book);
        bookComment.setComment(commentText);

        book.getBookComments()
                .add(bookComment);

        bookRepository.save(book);
    }
}
