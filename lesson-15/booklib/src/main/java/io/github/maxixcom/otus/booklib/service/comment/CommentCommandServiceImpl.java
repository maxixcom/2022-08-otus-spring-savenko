package io.github.maxixcom.otus.booklib.service.comment;

import io.github.maxixcom.otus.booklib.domain.Comment;
import io.github.maxixcom.otus.booklib.service.io.IOService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CommentCommandServiceImpl implements CommentCommandService {
    private final CommentService commentService;
    private final IOService ioService;

    private final CommentInteraction commentInteraction;

    @Override
    public void listBookComments(String bookId) {
        List<Comment> comments = commentService.getAllBookComments(bookId);
        if (comments.isEmpty()) {
            ioService.out("There are no comments on the book yet.%n%n");
            return;
        }

        comments.forEach(comment -> {
            ioService.out("%s : %s%n",
                    comment.getId(),
                    comment.getComment()
            );
        });
    }

    @Override
    public void createBookComment(String bookId) {
        commentService.createBookComment(
                bookId,
                commentInteraction.collectCommentCreateInfo()
        );
    }

    @Override
    public void updateBookComment(String commentId) {
        commentService.updateBookComment(
                commentId,
                commentInteraction.collectCommentUpdateInfo()
        );
    }

    @Override
    public void deleteBookComments(Set<String> commentIds) {
        commentService.deleteBookComments(commentIds);
        ioService.out(
                "Book comments #%s deleted%n",
                commentIds.stream()
                        .map(Object::toString)
                        .collect(Collectors.joining(", #"))
        );
    }
}
