package io.github.maxixcom.otus.booklib.command;

import io.github.maxixcom.otus.booklib.service.book.BookCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.Set;

@RequiredArgsConstructor
@ShellCommandGroup("Manage books")
@ShellComponent
public class BookCommand {
    private final BookCommandService bookCommandService;

    @ShellMethod(value = "List all books", key = {"l", "p", "list", "list-books"})
    public void listBooks() {
        bookCommandService.listBooks();
    }

    @ShellMethod(value = "Create book", key = {"n", "create-book"})
    public void createBook() {
        bookCommandService.createBook();
    }

    @ShellMethod(value = "Update book info", key = {"u", "e", "update", "edit", "update-book"})
    public void updateBook(long bookId) {
        bookCommandService.updateBook(bookId);
    }

    @ShellMethod(value = "Delete books", key = {"d", "del", "delete-books"})
    public void deleteBooks(Long[] bookIds) {
        bookCommandService.deleteBooks(Set.of(bookIds));
    }
}
