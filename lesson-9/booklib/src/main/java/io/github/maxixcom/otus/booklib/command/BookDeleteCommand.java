package io.github.maxixcom.otus.booklib.command;

import io.github.maxixcom.otus.booklib.service.BookDeleteService;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.Set;

@RequiredArgsConstructor
@ShellComponent
public class BookDeleteCommand {
    private final BookDeleteService bookDeleteService;

    @ShellMethod(value = "Delete books", key = {"d", "del", "delete-books"})
    public void deleteBooks(Long[] bookIds) {
        bookDeleteService.deleteBooks(Set.of(bookIds));
    }
}
