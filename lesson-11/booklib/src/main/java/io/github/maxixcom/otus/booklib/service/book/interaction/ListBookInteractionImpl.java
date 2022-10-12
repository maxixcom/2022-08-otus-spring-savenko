package io.github.maxixcom.otus.booklib.service.book.interaction;

import io.github.maxixcom.otus.booklib.domain.Author;
import io.github.maxixcom.otus.booklib.domain.Book;
import io.github.maxixcom.otus.booklib.domain.Genre;
import io.github.maxixcom.otus.booklib.service.io.IOService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ListBookInteractionImpl implements ListBookInteraction {
    private final IOService ioService;

    @Override
    public void listBooks(List<Book> books) {
        if (books.isEmpty()) {
            ioService.out("No books. Library is empty!%n%n");
            return;
        }

        printList(books);
    }

    private void printList(List<Book> books) {
        books.forEach(book ->
                ioService.out("%d. %s, %s (%s)%n",
                        book.getId(),
                        book.getTitle(),
                        Optional.ofNullable(book.getAuthor())
                                .map(Author::getName)
                                .orElse("No Author"),
                        Optional.ofNullable(book.getGenre())
                                .map(Genre::getTitle)
                                .orElse("No Genre")
                )
        );
    }
}
