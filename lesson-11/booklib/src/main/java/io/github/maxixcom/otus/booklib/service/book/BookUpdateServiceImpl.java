package io.github.maxixcom.otus.booklib.service.book;

import io.github.maxixcom.otus.booklib.domain.Author;
import io.github.maxixcom.otus.booklib.domain.Book;
import io.github.maxixcom.otus.booklib.domain.Genre;
import io.github.maxixcom.otus.booklib.repository.BookRepository;
import io.github.maxixcom.otus.booklib.service.io.IOService;
import io.github.maxixcom.otus.booklib.service.selector.AuthorSelectorService;
import io.github.maxixcom.otus.booklib.service.selector.GenreSelectorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.function.Consumer;

@RequiredArgsConstructor
@Service
public class BookUpdateServiceImpl implements BookUpdateService {
    private final IOService ioService;
    private final BookRepository bookRepository;
    private final AuthorSelectorService authorSelectorService;
    private final GenreSelectorService genreSelectorService;

    @Transactional
    @Override
    public void updateBook(long bookId) {
        bookRepository.findById(bookId)
                .ifPresentOrElse(
                        this::updateBookInteractive,
                        () -> ioService.out("Book #%d not found%n", bookId)
                );
    }

    private void updateBookInteractive(Book book) {

        updateBookTitle(book, newTitle -> {
            if (!newTitle.isBlank()) {
                book.setTitle(newTitle);
            }
        });

        selectBookAuthor(book, book::setAuthor);
        selectBookGenre(book, book::setGenre);

        Book updatedBook = bookRepository.save(book);

        ioService.out("%nBook #%d updated%n", updatedBook.getId());
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
