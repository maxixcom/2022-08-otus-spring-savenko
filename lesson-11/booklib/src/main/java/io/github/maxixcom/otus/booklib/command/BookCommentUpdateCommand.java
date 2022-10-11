
package io.github.maxixcom.otus.booklib.command;

import io.github.maxixcom.otus.booklib.service.comment.BookCommentUpdateService;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@RequiredArgsConstructor
@ShellCommandGroup("Manage book comments")
@ShellComponent
public class BookCommentUpdateCommand {
    private final BookCommentUpdateService bookCommentUpdateService;

    @ShellMethod(value = "Update book comment", key = {"uc", "ec", "update-comment"})
    public void updateComment(Long commentId) {
        bookCommentUpdateService.updateComment(commentId);
    }
}
