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
import java.util.function.Consumer;

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
                        this::updateBookInteractive,
                        () -> ioService.out("Book #%d not found%n", bookId)
                );
    }

    private void updateBookInteractive(Book book) {
        Book.BookBuilder bookBuilder = book.toBuilder();

        updateBookTitle(book, newTitle -> {
            if (!newTitle.isBlank()) {
                bookBuilder.title(newTitle);
            }
        });

        selectBookAuthor(book, bookBuilder::author);
        selectBookGenre(book, bookBuilder::genre);

        bookDao.update(bookBuilder.build());

        ioService.out("%nBook #%d updated%n", book.getId());
    }

    private void updateBookTitle(Book book, Consumer<String> updateFunction) {
        String newTitle = ioService.readLineWithPrompt(
                "Title (%s - enter to leave current): ",
                book.getTitle()
        );
        updateFunction.accept(newTitle);
    }

    private void selectBookAuthor(Book book, Consumer<Author> updateFunction) {
        while (true) {
            String selectCommand = ioService.readLineWithPrompt(
                    "Author (%s - enter to leave current, r - reset, l - for select): ",
                    Optional.ofNullable(book.getAuthor())
                            .map(Author::getName)
                            .orElse("Not Set")
            );

            if (selectCommand.equals("l")) {
                authorSelectorService.selectAuthor()
                        .ifPresent(updateFunction);
                break;
            }

            if (selectCommand.equals("r")) {
                updateFunction.accept(null);
                break;
            }

            if (selectCommand.isBlank()) {
                break;
            }
        }
    }

    private void selectBookGenre(Book book, Consumer<Genre> updateFunction) {
        while (true) {
            String selectCommand = ioService.readLineWithPrompt(
                    "Genre (%s - enter to leave current, r - reset, l - for select): ",
                    Optional.ofNullable(book.getGenre())
                            .map(Genre::getTitle)
                            .orElse("Not Set")
            );

            if (selectCommand.equals("l")) {
                genreSelectorService.selectGenre()
                        .ifPresent(updateFunction);
                break;
            }

            if (selectCommand.equals("r")) {
                updateFunction.accept(null);
                break;
            }

            if (selectCommand.isBlank()) {
                break;
            }
        }
    }
}
