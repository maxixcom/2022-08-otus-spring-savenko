
package io.github.maxixcom.otus.booklib.command;

import io.github.maxixcom.otus.booklib.service.book.BookCreateService;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@RequiredArgsConstructor
@ShellCommandGroup("Manage books")
@ShellComponent
public class BookCreateCommand {
    private final BookCreateService bookCreateService;

    @ShellMethod(value = "Create book", key = {"n", "create-book"})
    public void createBook() {
        bookCreateService.createBook();
    }
}
