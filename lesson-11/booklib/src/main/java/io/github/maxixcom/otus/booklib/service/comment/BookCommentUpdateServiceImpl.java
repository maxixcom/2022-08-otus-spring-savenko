package io.github.maxixcom.otus.booklib.service.comment;

import io.github.maxixcom.otus.booklib.domain.BookComment;
import io.github.maxixcom.otus.booklib.repository.BookCommentRepository;
import io.github.maxixcom.otus.booklib.service.io.IOService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class BookCommentUpdateServiceImpl implements BookCommentUpdateService {
    private final IOService ioService;
    private final BookCommentRepository bookCommentRepository;

    @Transactional
    @Override
    public void updateComment(long commentId) {
        bookCommentRepository.findById(commentId)
                .ifPresentOrElse(
                        this::updateCommentInteractive,
                        () -> ioService.out("Book comment #%d not found%n", commentId)
                );
    }

    private void updateCommentInteractive(BookComment bookComment) {
        String commentText = ioService.readLineWithPrompt("Enter new text of comment: ");

        bookComment.setComment(commentText);
        bookCommentRepository.save(bookComment);
    }
}
