package io.github.maxixcom.otus.booklib.service.comment;

import java.util.Set;

public interface CommentCommandService {
    void listBookComments(String bookId);

    void createBookComment(String bookId);

    void updateBookComment(String commentId);

    void deleteBookComments(Set<String> commentIds);
}
