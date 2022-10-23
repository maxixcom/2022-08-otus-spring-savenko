package io.github.maxixcom.otus.booklib.service.book;

import io.github.maxixcom.otus.booklib.domain.Author;
import io.github.maxixcom.otus.booklib.domain.Book;
import io.github.maxixcom.otus.booklib.domain.Genre;
import io.github.maxixcom.otus.booklib.service.book.dto.CreateBookDto;
import io.github.maxixcom.otus.booklib.service.book.dto.UpdateBookDto;
import io.github.maxixcom.otus.booklib.service.io.IOService;
import io.github.maxixcom.otus.booklib.service.selector.AuthorSelectorService;
import io.github.maxixcom.otus.booklib.service.selector.GenreSelectorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

@RequiredArgsConstructor
@Service
public class BookInteractionImpl implements BookInteraction {
    private final IOService ioService;
    private final AuthorSelectorService authorSelectorService;
    private final GenreSelectorService genreSelectorService;

    @Override
    public CreateBookDto collectBookCreateInfo() {
        return new CreateBookDto(
                inputBookTitle(),
                selectBookAuthor().orElse(null),
                selectBookGenre().orElse(null)
        );
    }

    @Override
    public UpdateBookDto collectBookUpdateInfo(Book book) {
        UpdateBookDto updateBookDto = new UpdateBookDto();
        updateBookDto.setBookId(book.getId().toString());
        updateBookDto.setTitle(book.getTitle());
        updateBookDto.setAuthor(book.getAuthor());
        updateBookDto.setGenre(book.getGenre());

        updateBookTitle(book, newTitle -> {
            if (!newTitle.isBlank()) {
                updateBookDto.setTitle(newTitle);
            }
        });

        updateBookAuthor(book, updateBookDto::setAuthor);
        updateBookGenre(book, updateBookDto::setGenre);

        return updateBookDto;
    }

    @Override
    public void listBooks(List<Book> books) {
        if (books.isEmpty()) {
            ioService.out("No books. Library is empty!%n%n");
            return;
        }

        books.forEach(book ->
                ioService.out("%s : %s, %s (%s)%n",
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

    private String inputBookTitle() {
        while (true) {
            String newTitle = ioService.readLineWithPrompt("Title: ");
            if (!newTitle.isBlank()) {
                return newTitle;
            }
        }
    }

    private Optional<Author> selectBookAuthor() {
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

    private Optional<Genre> selectBookGenre() {
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

    private void updateBookTitle(Book book, Consumer<String> updateFunction) {
        String newTitle = ioService.readLineWithPrompt(
                "Title (%s - enter to leave current): ",
                book.getTitle()
        );
        updateFunction.accept(newTitle);
    }

    private void updateBookAuthor(Book book, Consumer<Author> updateFunction) {
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

    private void updateBookGenre(Book book, Consumer<Genre> updateFunction) {
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
