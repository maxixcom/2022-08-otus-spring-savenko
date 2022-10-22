package io.github.maxixcom.otus.booklib.service.comment;

import java.util.Set;

public interface BookCommentCommandService {
    void listBookComments(long bookId);

    void createBookComment(long bookId);

    void updateBookComment(long commentId);

    void deleteBookComments(Set<Long> commentIds);
}
