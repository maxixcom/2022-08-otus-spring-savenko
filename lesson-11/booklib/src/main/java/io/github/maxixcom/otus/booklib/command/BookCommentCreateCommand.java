
package io.github.maxixcom.otus.booklib.command;

import io.github.maxixcom.otus.booklib.service.comment.BookCommentCreateService;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@RequiredArgsConstructor
@ShellCommandGroup("Manage book comments")
@ShellComponent
public class BookCommentCreateCommand {
    private final BookCommentCreateService bookCommentCreateService;

    @ShellMethod(value = "Create book comment", key = {"nc", "comment-book"})
    public void commentBook(long bookId) {
        bookCommentCreateService.createComment(bookId);
    }
}
