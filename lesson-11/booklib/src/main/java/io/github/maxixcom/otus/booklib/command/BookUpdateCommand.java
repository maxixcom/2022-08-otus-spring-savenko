package io.github.maxixcom.otus.booklib.command;

import io.github.maxixcom.otus.booklib.service.book.BookUpdateService;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@RequiredArgsConstructor
@ShellCommandGroup("Manage books")
@ShellComponent
public class BookUpdateCommand {
    private final BookUpdateService bookUpdateService;

    @ShellMethod(value = "Update book info", key = {"u", "e", "update", "edit", "update-book"})
    public void updateBook(long bookId) {
        bookUpdateService.updateBook(bookId);
    }
}
