package io.github.maxixcom.otus.booklib.service.book;

import io.github.maxixcom.otus.booklib.domain.Book;
import io.github.maxixcom.otus.booklib.service.book.interaction.CreateBookInteraction;
import io.github.maxixcom.otus.booklib.service.book.interaction.ListBookInteraction;
import io.github.maxixcom.otus.booklib.service.book.interaction.UpdateBookInteraction;
import io.github.maxixcom.otus.booklib.service.io.IOService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BookCommandServiceImpl implements BookCommandService {
    private final BookService bookService;
    private final IOService ioService;
    private final ListBookInteraction listBookInteraction;
    private final CreateBookInteraction createBookInteraction;
    private final UpdateBookInteraction updateBookInteraction;

    @Override
    public void listBooks() {
        List<Book> books = bookService.getAllBooks();
        listBookInteraction.listBooks(books);
    }

    @Override
    public void createBook() {
        BookService.CreateBookDto bookDto = createBookInteraction.collectBookInfo();
        Book book = bookService.createBook(bookDto);
        ioService.out("%nBook #%d created%n", book.getId());
    }

    @Override
    public void updateBook(long bookId) {
        bookService.getBookById(bookId)
                .map(updateBookInteraction::collectBookUpdateInfo)
                .map(bookService::updateBook)
                .ifPresentOrElse(
                        book -> ioService.out("%nBook #%d updated%n", book.getId()),
                        () -> ioService.out("Book #%d not found%n", bookId)
                );
    }

    @Override
    public void deleteBooks(Set<Long> bookIds) {
        if (bookIds.isEmpty()) {
            ioService.out("Nothing to delete");
            return;
        }

        bookService.deleteBooks(bookIds);

        ioService.out(
                "Books #%s deleted%n",
                bookIds.stream()
                        .map(Object::toString)
                        .collect(Collectors.joining(", #"))
        );
    }
}
