package io.github.maxixcom.otus.booklib.service.book.interaction;

import io.github.maxixcom.otus.booklib.domain.Author;
import io.github.maxixcom.otus.booklib.domain.Book;
import io.github.maxixcom.otus.booklib.domain.Genre;
import io.github.maxixcom.otus.booklib.service.book.BookService;
import io.github.maxixcom.otus.booklib.service.io.IOService;
import io.github.maxixcom.otus.booklib.service.selector.AuthorSelectorService;
import io.github.maxixcom.otus.booklib.service.selector.GenreSelectorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Consumer;

@RequiredArgsConstructor
@Service
public class UpdateBookInteractionImpl implements UpdateBookInteraction {
    private final IOService ioService;
    private final AuthorSelectorService authorSelectorService;
    private final GenreSelectorService genreSelectorService;

    @Override
    public BookService.UpdateBookDto collectBookUpdateInfo(Book book) {
        BookService.UpdateBookDto updateBookDto = new BookService.UpdateBookDto();

        updateBookDto.setBookId(book.getId());
        updateBookDto.setTitle(book.getTitle());
        updateBookDto.setAuthorId(
                Optional.ofNullable(book.getAuthor())
                        .map(Author::getId)
                        .orElse(null)
        );
        updateBookDto.setGenreId(
                Optional.ofNullable(book.getGenre())
                        .map(Genre::getId)
                        .orElse(null)
        );

        updateBookTitle(book, newTitle -> {
            if (!newTitle.isBlank()) {
                updateBookDto.setTitle(newTitle);
            }
        });

        selectBookAuthor(book, updateBookDto::setAuthorId);
        selectBookGenre(book, updateBookDto::setGenreId);

        return updateBookDto;
    }

    private void updateBookTitle(Book book, Consumer<String> updateFunction) {
        String newTitle = ioService.readLineWithPrompt(
                "Title (%s - enter to leave current): ",
                book.getTitle()
        );
        updateFunction.accept(newTitle);
    }

    private void selectBookAuthor(Book book, Consumer<Long> updateFunction) {
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

    private void selectBookGenre(Book book, Consumer<Long> updateFunction) {
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
