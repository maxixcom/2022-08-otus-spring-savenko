package io.github.maxixcom.otus.booklib.service.book;

import io.github.maxixcom.otus.booklib.domain.Book;
import io.github.maxixcom.otus.booklib.service.book.dto.CreateBookDto;
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
    private final BookInteraction bookInteraction;

    @Override
    public void listBooks() {
        List<Book> books = bookService.getAllBooks();
        bookInteraction.listBooks(books);
    }

    @Override
    public void createBook() {
        CreateBookDto bookDto = bookInteraction.collectBookCreateInfo();
        Book book = bookService.createBook(bookDto);
        ioService.out("%nBook %s created%n", book.getId());
    }

    @Override
    public void updateBook(String bookId) {
        bookService.getBookById(bookId)
                .map(bookInteraction::collectBookUpdateInfo)
                .map(bookService::updateBook)
                .ifPresentOrElse(
                        book -> ioService.out("%nBook #%s updated%n", book.getId()),
                        () -> ioService.out("Book #%s not found%n", bookId)
                );
    }

    @Override
    public void deleteBooks(Set<String> bookIds) {
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
