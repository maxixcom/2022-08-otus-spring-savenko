package io.github.maxixcom.otus.booklib.command;

import io.github.maxixcom.otus.booklib.service.BookDeleteService;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@ShellComponent
public class DeleteBookCommand {
    private final BookDeleteService bookDeleteService;

    @ShellMethod(value = "Delete books", key = {"d", "del", "delete-book"})
    public void deleteBook(Long[] bookIds) {
        bookDeleteService.deleteBooks(Set.of(bookIds));
    }
}
