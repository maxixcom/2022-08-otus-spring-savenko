package io.github.maxixcom.otus.booklib.service.book.interaction;

import io.github.maxixcom.otus.booklib.service.book.BookService;
import io.github.maxixcom.otus.booklib.service.io.IOService;
import io.github.maxixcom.otus.booklib.service.selector.AuthorSelectorService;
import io.github.maxixcom.otus.booklib.service.selector.GenreSelectorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CreateBookInteractionImpl implements CreateBookInteraction {
    private final IOService ioService;
    private final AuthorSelectorService authorSelectorService;
    private final GenreSelectorService genreSelectorService;

    @Override
    public BookService.CreateBookDto collectBookInfo() {
        return new BookService.CreateBookDto(
                inputBookTitle(),
                selectBookAuthor().orElse(null),
                selectBookGenre().orElse(null)
        );
    }

    private String inputBookTitle() {
        while (true) {
            String newTitle = ioService.readLineWithPrompt("Title: ");
            if (!newTitle.isBlank()) {
                return newTitle;
            }
        }
    }

    private Optional<Long> selectBookAuthor() {
        while (true) {
            String selectCommand = ioService.readLineWithPrompt("Author (skip - enter, l - for select): ");

            if (selectCommand.equals("l")) {
                return authorSelectorService.selectAuthor();
            }

            if (selectCommand.isBlank()) {
                return Optional.empty();
            }
        }
    }

    private Optional<Long> selectBookGenre() {
        while (true) {
            String selectCommand = ioService.readLineWithPrompt("Genre (skip - enter, l - for select): ");

            if (selectCommand.equals("l")) {
                return genreSelectorService.selectGenre();
            }

            if (selectCommand.isBlank()) {
                return Optional.empty();
            }
        }
    }
}
