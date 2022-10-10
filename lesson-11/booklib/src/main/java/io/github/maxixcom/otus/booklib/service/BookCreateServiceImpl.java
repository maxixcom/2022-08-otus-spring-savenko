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
public class BookCreateServiceImpl implements BookCreateService {
    private final IOService ioService;
    private final BookDao bookDao;
    private final AuthorSelectorService authorSelectorService;
    private final GenreSelectorService genreSelectorService;

    @Override
    public void createBook() {
        Book book = collectBookInfo();

        long bookId = bookDao.insert(book);

        ioService.out("%nBook #%d created%n", bookId);
    }

    private Book collectBookInfo() {
        Book.BookBuilder bookBuilder = Book.builder()
                .title(inputBookTitle())
                .author(selectBookAuthor().orElse(null))
                .genre(selectBookGenre().orElse(null));

        return bookBuilder.build();
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
}
