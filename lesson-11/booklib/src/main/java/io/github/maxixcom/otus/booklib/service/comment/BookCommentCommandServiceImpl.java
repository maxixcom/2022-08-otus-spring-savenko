package io.github.maxixcom.otus.booklib.service.comment;

import io.github.maxixcom.otus.booklib.domain.BookComment;
import io.github.maxixcom.otus.booklib.service.io.IOService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BookCommentCommandServiceImpl implements BookCommentCommandService {
    private final BookCommentService bookCommentService;
    private final IOService ioService;

    private final BookCommentInteraction bookCommentInteraction;

    @Override
    public void listBookComments(long bookId) {
        List<BookComment> bookComments = bookCommentService.getAllBookComments(bookId);
        if (bookComments.isEmpty()) {
            ioService.out("There are no comments on the book yet.%n%n");
        }

        bookComments.forEach(comment -> {
            ioService.out("%d. %s%n",
                    comment.getId(),
                    comment.getComment()
            );
        });
    }

    @Override
    public void createBookComment(long bookId) {
        bookCommentService.createBookComment(
                bookId,
                bookCommentInteraction.collectCommentCreateInfo()
        );
    }

    @Override
    public void updateBookComment(long commentId) {
        bookCommentService.updateBookComment(
                commentId,
                bookCommentInteraction.collectCommentUpdateInfo()
        );
    }

    @Override
    public void deleteBookComments(Set<Long> commentIds) {
        bookCommentService.deleteBookComments(commentIds);
        ioService.out(
                "Book comments #%s deleted%n",
                commentIds.stream()
                        .map(Object::toString)
                        .collect(Collectors.joining(", #"))
        );
    }
}
