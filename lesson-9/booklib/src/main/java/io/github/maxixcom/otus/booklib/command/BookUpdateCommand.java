package io.github.maxixcom.otus.booklib.command;

import io.github.maxixcom.otus.booklib.service.BookUpdateService;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@RequiredArgsConstructor
@ShellComponent
public class BookUpdateCommand {
    private final BookUpdateService bookUpdateService;

    @ShellMethod(value = "Update book info", key = {"u", "e", "update", "edit", "update-book"})
    public void updateBook(Long bookId) {
        bookUpdateService.updateBook(bookId);
    }
}
