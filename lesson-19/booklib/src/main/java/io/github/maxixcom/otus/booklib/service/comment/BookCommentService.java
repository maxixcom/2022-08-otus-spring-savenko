package io.github.maxixcom.otus.booklib.service.comment;

import io.github.maxixcom.otus.booklib.domain.BookComment;

import java.util.List;
import java.util.Set;

public interface BookCommentService {
    List<BookComment> getAllBookComments(long bookId);

    void createBookComment(long bookId, String commentText);

    void updateBookComment(long commentId, String commentText);

    void deleteBookComments(Set<Long> commentIds);
}
