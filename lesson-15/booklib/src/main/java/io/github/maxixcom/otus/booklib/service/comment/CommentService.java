package io.github.maxixcom.otus.booklib.service.comment;

import io.github.maxixcom.otus.booklib.domain.Comment;

import java.util.List;
import java.util.Set;

public interface CommentService {
    List<Comment> getAllBookComments(String bookId);

    void createBookComment(String bookId, String commentText);

    void updateBookComment(String commentId, String commentText);

    void deleteBookComments(Set<String> commentIds);
}
