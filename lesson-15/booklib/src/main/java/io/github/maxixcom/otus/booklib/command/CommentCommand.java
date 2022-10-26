package io.github.maxixcom.otus.booklib.command;

import io.github.maxixcom.otus.booklib.service.comment.CommentCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.Set;

@RequiredArgsConstructor
@ShellCommandGroup("Manage book comments")
@ShellComponent
public class CommentCommand {
    private final CommentCommandService commentCommandService;

    @ShellMethod(value = "List book comments", key = {"lc", "pc", "list-book-comment"})
    public void listBookComments(String bookId) {
        commentCommandService.listBookComments(bookId);
    }

    @ShellMethod(value = "Create book comment", key = {"nc", "comment-book"})
    public void commentBook(String bookId) {
        commentCommandService.createBookComment(bookId);
    }

    @ShellMethod(value = "Update book comment", key = {"uc", "ec", "update-comment"})
    public void updateComment(String commentId) {
        commentCommandService.updateBookComment(commentId);
    }

    @ShellMethod(value = "Delete book comments", key = {"dc", "delete-comment"})
    public void deleteComment(String[] commentIds) {
        commentCommandService.deleteBookComments(Set.of(commentIds));
    }
}
