package io.github.maxixcom.otus.booklib.command;

import io.github.maxixcom.otus.booklib.service.comment.BookCommentDeleteService;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.Set;

@RequiredArgsConstructor
@ShellCommandGroup("Manage book comments")
@ShellComponent
public class BookCommentDeleteCommand {
    private final BookCommentDeleteService bookCommentDeleteService;

    @ShellMethod(value = "Delete book comments", key = {"dc", "delete-comment"})
    public void deleteComment(Long[] commentIds) {
        bookCommentDeleteService.deleteComment(Set.of(commentIds));
    }
}
