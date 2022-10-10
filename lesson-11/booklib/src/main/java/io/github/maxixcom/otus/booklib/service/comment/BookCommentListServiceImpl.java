package io.github.maxixcom.otus.booklib.service.comment;

import io.github.maxixcom.otus.booklib.domain.Book;
import io.github.maxixcom.otus.booklib.domain.BookComment;
import io.github.maxixcom.otus.booklib.repository.BookRepository;
import io.github.maxixcom.otus.booklib.service.io.IOService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BookCommentListServiceImpl implements BookCommentListService {
    private final IOService ioService;
    private final BookRepository bookRepository;

    @Transactional(readOnly = true)
    @Override
    public void outListOfAllBookComments(long bookId) {
        bookRepository.findByIdWithComments(bookId)
                .ifPresentOrElse(
                        this::listBookComments,
                        () -> ioService.out("Book #%d not found%n", bookId)
                );
    }

    private void listBookComments(Book book) {
        List<BookComment> bookComments = book.getBookComments();

        if (bookComments.isEmpty()) {
            ioService.out("There are no comments on the book yet.%n%n");
            return;
        }

        bookComments.forEach(comment -> {
            ioService.out("%d. %s%n",
                    comment.getId(),
                    comment.getComment()
            );
        });
    }
}
