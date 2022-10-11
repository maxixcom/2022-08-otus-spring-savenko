
package io.github.maxixcom.otus.booklib.command;

import io.github.maxixcom.otus.booklib.service.comment.BookCommentListService;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@RequiredArgsConstructor
@ShellCommandGroup("Manage book comments")
@ShellComponent
public class BookCommentListCommand {
    private final BookCommentListService bookCommentListService;

    @ShellMethod(value = "List book comments", key = {"lc", "pc", "list-book-comment"})
    public void listBookComments(long bookId) {
        bookCommentListService.outListOfAllBookComments(bookId);
    }
}
