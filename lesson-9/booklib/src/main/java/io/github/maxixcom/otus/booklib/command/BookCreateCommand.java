
package io.github.maxixcom.otus.booklib.command;

import io.github.maxixcom.otus.booklib.service.BookCreateService;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@RequiredArgsConstructor
@ShellComponent
public class BookCreateCommand {
    private final BookCreateService bookCreateService;

    @ShellMethod(value = "Create book", key = {"c", "n", "create-book"})
    public void createBook() {
        bookCreateService.createBook();
    }
}
