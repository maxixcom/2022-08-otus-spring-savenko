package io.github.maxixcom.otus.booklib.command;

import io.github.maxixcom.otus.booklib.service.BookListingService;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@RequiredArgsConstructor
@ShellComponent
public class BookListCommand {
    private final BookListingService bookListingService;

    @ShellMethod(value = "List all books", key = {"l", "p", "list", "list-books"})
    public void listBooks() {
        bookListingService.outListOfAllBooks();
    }
}
