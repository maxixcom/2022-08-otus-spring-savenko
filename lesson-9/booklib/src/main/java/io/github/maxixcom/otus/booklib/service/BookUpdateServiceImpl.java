package io.github.maxixcom.otus.booklib.service;

import io.github.maxixcom.otus.booklib.dao.BookDao;
import io.github.maxixcom.otus.booklib.domain.Author;
import io.github.maxixcom.otus.booklib.domain.Book;
import io.github.maxixcom.otus.booklib.domain.Genre;
import io.github.maxixcom.otus.booklib.service.io.IOService;
import io.github.maxixcom.otus.booklib.service.selector.AuthorSelectorService;
import io.github.maxixcom.otus.booklib.service.selector.GenreSelectorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class BookUpdateServiceImpl implements BookUpdateService {
    private final IOService ioService;
    private final BookDao bookDao;
    private final AuthorSelectorService authorSelectorService;
    private final GenreSelectorService genreSelectorService;

    @Override
    public void updateBook(long bookId) {
        bookDao.findById(bookId)
                .ifPresentOrElse(
                        this::runUpdateBookScenario,
                        () -> ioService.out("Book #%d not found%n", bookId)
                );
    }

    private void runUpdateBookScenario(Book book) {
        Book.BookBuilder bookBuilder = book.toBuilder();
        String newTitle = ioService.readLineWithPrompt(
                "Title (%s - enter to leave current): ",
                book.getTitle()
        );
        if (!newTitle.isBlank()) {
            bookBuilder.title(newTitle);
        }

        selectBookAuthor(book, bookBuilder);
        selectBookGenre(book, bookBuilder);

        bookDao.update(bookBuilder.build());

        ioService.out("%nBook #%d updated%n", book.getId());
    }

    private void selectBookAuthor(Book book, Book.BookBuilder bookBuilder) {
        while (true) {
            String selectCommand = ioService.readLineWithPrompt(
                    "Author (%s - enter to leave current, r - reset, l - for select): ",
                    Optional.ofNullable(book.getAuthor())
                            .map(Author::getName)
                            .orElse("Not Set")
            );

            if (selectCommand.equals("l")) {
                authorSelectorService.selectAuthor()
                        .ifPresent(bookBuilder::author);
                break;
            }
            if (selectCommand.equals("r")) {
                bookBuilder.author(null);
                break;
            }
            if (selectCommand.isBlank()) {
                break;
            }
        }
    }

    private void selectBookGenre(Book book, Book.BookBuilder bookBuilder) {
        while (true) {
            String selectCommand = ioService.readLineWithPrompt(
                    "Genre (%s - enter to leave current, r - reset, l - for select): ",
                    Optional.ofNullable(book.getGenre())
                            .map(Genre::getTitle)
                            .orElse("Not Set")
            );

            if (selectCommand.equals("l")) {
                genreSelectorService.selectGenre()
                        .ifPresent(bookBuilder::genre);
                break;
            }
            if (selectCommand.equals("r")) {
                bookBuilder.genre(null);
                break;
            }
            if (selectCommand.isBlank()) {
                break;
            }
        }
    }
}
