package io.github.maxixcom.otus.booklib.command;

import io.github.maxixcom.otus.booklib.service.comment.BookCommentCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.Set;

@RequiredArgsConstructor
@ShellCommandGroup("Manage book comments")
@ShellComponent
public class BookCommentCommand {
    private final BookCommentCommandService bookCommentCommandService;

    @ShellMethod(value = "List book comments", key = {"lc", "pc", "list-book-comment"})
    public void listBookComments(long bookId) {
        bookCommentCommandService.listBookComments(bookId);
    }

    @ShellMethod(value = "Create book comment", key = {"nc", "comment-book"})
    public void commentBook(long bookId) {
        bookCommentCommandService.createBookComment(bookId);
    }

    @ShellMethod(value = "Update book comment", key = {"uc", "ec", "update-comment"})
    public void updateComment(Long commentId) {
        bookCommentCommandService.updateBookComment(commentId);
    }

    @ShellMethod(value = "Delete book comments", key = {"dc", "delete-comment"})
    public void deleteComment(Long[] commentIds) {
        bookCommentCommandService.deleteBookComments(Set.of(commentIds));
    }
}
